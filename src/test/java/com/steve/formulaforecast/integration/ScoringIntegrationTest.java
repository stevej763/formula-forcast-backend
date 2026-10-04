package com.steve.formulaforecast.integration;

import com.neovisionaries.i18n.CountryCode;
import com.steve.formulaforecast.TestcontainersConfiguration;
import com.steve.formulaforecast.api.prediction.model.prediction.RankedDriverPrediction;
import com.steve.formulaforecast.persistence.AccountRepository;
import com.steve.formulaforecast.service.leaderboard.ChampionshipLeaderboardWithEntries;
import com.steve.formulaforecast.service.leaderboard.LeaderboardEntry;
import com.steve.formulaforecast.service.leaderboard.LeaderboardService;
import com.steve.formulaforecast.service.prediction.DriverPrediction;
import com.steve.formulaforecast.service.prediction.PredictionException;
import com.steve.formulaforecast.service.prediction.PredictionService;
import com.steve.formulaforecast.service.raceweekends.RaceWeekendDetailsService;
import com.steve.formulaforecast.service.raceweekends.model.RaceName;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekend;
import com.steve.formulaforecast.service.result.PredictionResult;
import com.steve.formulaforecast.service.result.PredictionResultService;
import com.steve.formulaforecast.service.result.ResultException;
import com.steve.formulaforecast.service.team.UserTeamService;
import com.steve.formulaforecast.service.team.model.UserTeamCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.steve.formulaforecast.service.prediction.PredictionException.PREDICTIONS_LOCKED;
import static com.steve.formulaforecast.service.result.ResultException.RESULTS_BEFORE_LOCK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Runs picks, locking, results, scoring and standings against the real schema and seed data,
 * using the 2026 Singapore Grand Prix (round 17, qualifying 10 October 2026 at 13:00 UTC).
 */
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@Transactional
class ScoringIntegrationTest {

    private static final Instant BEFORE_LOCK = Instant.parse("2026-10-08T12:00:00Z");
    private static final Instant AFTER_LOCK = Instant.parse("2026-10-11T15:00:00Z");

    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserTeamService userTeamService;
    @Autowired
    private PredictionService predictionService;
    @Autowired
    private PredictionResultService predictionResultService;
    @Autowired
    private LeaderboardService leaderboardService;
    @Autowired
    private RaceWeekendDetailsService raceWeekendDetailsService;

    @MockitoBean
    private InstantSource instantSource;

    private UUID singapore;
    private UUID raceTopThree;
    private UUID fastestLap;

    @BeforeEach
    void setUp() {
        givenNow(BEFORE_LOCK);
        singapore = jdbc.queryForObject("""
                SELECT race_weekend_uid FROM race_weekend
                JOIN championship_season ON race_weekend.championship_season_id = championship_season.id
                WHERE championship_year = '2026' AND round_number = 17
                """, Map.of(), UUID.class);
        raceTopThree = predictionTypeUid("RACE_TOP_THREE");
        fastestLap = predictionTypeUid("FASTEST_LAP");
    }

    @Test
    void shouldScorePicksAgainstResultsAndRankTheStandings() {
        UUID topThreeTeam = aTeam("Top three team");
        UUID fastestLapTeam = aTeam("Fastest lap team");
        predictionService.makeDriverPrediction(new DriverPrediction(raceTopThree, topThreeTeam, singapore,
                List.of(pick("Russell", 1), pick("Leclerc", 2), pick("Verstappen", 3))));
        // Swapping positions replaces the picks rather than tripping the one driver per prediction constraint
        predictionService.makeDriverPrediction(new DriverPrediction(raceTopThree, topThreeTeam, singapore,
                List.of(pick("Russell", 1), pick("Verstappen", 2), pick("Leclerc", 3))));
        predictionService.makeDriverPrediction(new DriverPrediction(fastestLap, fastestLapTeam, singapore,
                List.of(pick("Norris", 1))));

        ResultException tooEarly = assertThrows(ResultException.class, () -> submitResult(raceTopThree, pick("Russell", 1), pick("Verstappen", 2), pick("Norris", 3)));
        assertEquals(RESULTS_BEFORE_LOCK, tooEarly.getMessage());

        givenNow(AFTER_LOCK);
        PredictionException locked = assertThrows(PredictionException.class, () -> predictionService.makeDriverPrediction(
                new DriverPrediction(fastestLap, topThreeTeam, singapore, List.of(pick("Norris", 1)))));
        assertEquals(PREDICTIONS_LOCKED, locked.getMessage());

        assertEquals(1, submitResult(raceTopThree, pick("Russell", 1), pick("Verstappen", 2), pick("Norris", 3)));
        assertEquals(1, submitResult(fastestLap, pick("Norris", 1)));

        assertEquals(2, pointsFor(topThreeTeam, raceTopThree));
        assertEquals(1, pointsFor(fastestLapTeam, fastestLap));
        assertNull(pointsFor(topThreeTeam, fastestLap), "no prediction made");
        assertStandings(List.of("Top three team", "Fastest lap team"), List.of(2L, 1L));

        // A corrected result re-scores from scratch
        submitResult(raceTopThree, pick("Russell", 1), pick("Leclerc", 2), pick("Norris", 3));
        assertEquals(1, pointsFor(topThreeTeam, raceTopThree));
        assertStandings(List.of("Fastest lap team", "Top three team"), List.of(1L, 1L));
    }

    @Test
    void shouldRenumberTheSeasonWhenAWeekendIsAddedBeforeExistingRounds() {
        RaceWeekend preSeason = raceWeekendDetailsService.createRaceWeekend(
                RaceName.BAHRAIN, CountryCode.BH,
                LocalDate.of(2026, 2, 27), LocalDate.of(2026, 3, 1),
                Instant.parse("2026-02-28T15:00:00Z"), Instant.parse("2026-03-01T15:00:00Z"));

        assertEquals(1, preSeason.getRoundNumber());
        Integer australiaRound = jdbc.queryForObject("""
                SELECT round_number FROM race_weekend
                JOIN championship_season ON race_weekend.championship_season_id = championship_season.id
                WHERE championship_year = '2026' AND race_name = 'AUSTRALIA'
                """, Map.of(), Integer.class);
        assertEquals(2, australiaRound);
        assertEquals(24, raceWeekendDetailsService.getRaceWeekendsForCurrentSeason().size());
        // The test transaction rolls back, so check the deferred season and round constraint now rather than at commit
        jdbc.getJdbcTemplate().execute("SET CONSTRAINTS ALL IMMEDIATE");
    }

    private int submitResult(UUID predictionTypeUid, RankedDriverPrediction... drivers) {
        return predictionResultService.submitResult(new PredictionResult(singapore, predictionTypeUid, List.of(drivers)));
    }

    private Integer pointsFor(UUID userTeamUid, UUID predictionTypeUid) {
        return predictionService.getDriverPredictionsForRaceWeekendForTeam(singapore, userTeamUid).get(predictionTypeUid).getPoints();
    }

    private void assertStandings(List<String> teamNames, List<Long> points) {
        ChampionshipLeaderboardWithEntries leaderboard = leaderboardService.getCurrentGlobalLeaderboard();
        assertEquals(teamNames, leaderboard.getEntries().stream().map(LeaderboardEntry::getTeamName).toList());
        assertEquals(points, leaderboard.getEntries().stream().map(LeaderboardEntry::getPoints).toList());
    }

    private UUID aTeam(String teamName) {
        UUID accountUid = UUID.randomUUID();
        accountRepository.insertAccount(accountUid, "Test", "Driver", accountUid + "@example.com", null, "password", BEFORE_LOCK);
        UUID teamUid = UUID.randomUUID();
        userTeamService.createTeam(accountUid, new UserTeamCreationRequest(teamUid, teamName, "#E3202B"));
        return teamUid;
    }

    private RankedDriverPrediction pick(String lastName, int rank) {
        UUID driverUid = jdbc.queryForObject("SELECT driver_uid FROM driver WHERE last_name = :lastName", Map.of("lastName", lastName), UUID.class);
        return new RankedDriverPrediction(driverUid, rank);
    }

    private UUID predictionTypeUid(String predictionType) {
        return jdbc.queryForObject("SELECT prediction_type_uid FROM prediction_type WHERE prediction_type = :type", Map.of("type", predictionType), UUID.class);
    }

    private void givenNow(Instant now) {
        when(instantSource.instant()).thenReturn(now);
    }
}
