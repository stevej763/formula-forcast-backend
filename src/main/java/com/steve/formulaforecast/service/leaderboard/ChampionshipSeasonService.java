package com.steve.formulaforecast.service.leaderboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.InstantSource;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.steve.formulaforecast.service.leaderboard.ChampionshipSeasonException.NO_CURRENT_SEASON;
import static com.steve.formulaforecast.service.leaderboard.ChampionshipSeasonException.SEASON_ALREADY_EXISTS;

@Service
public class ChampionshipSeasonService {

    private static final ZoneId LONDON_ZONE = ZoneId.of("Europe/London");
    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipSeasonService.class);

    private final ChampionshipSeasonPersistenceService championshipSeasonPersistenceService;
    private final ChampionshipLeaderboardPersistenceService championshipLeaderboardPersistenceService;
    private final LeaderboardEntryPersistenceService leaderboardEntryPersistenceService;
    private final InstantSource instantSource;

    ChampionshipSeasonService(
            ChampionshipSeasonPersistenceService championshipSeasonPersistenceService,
            ChampionshipLeaderboardPersistenceService championshipLeaderboardPersistenceService,
            LeaderboardEntryPersistenceService leaderboardEntryPersistenceService,
            InstantSource instantSource) {
        this.championshipSeasonPersistenceService = championshipSeasonPersistenceService;
        this.championshipLeaderboardPersistenceService = championshipLeaderboardPersistenceService;
        this.leaderboardEntryPersistenceService = leaderboardEntryPersistenceService;
        this.instantSource = instantSource;
    }

    @Transactional
    public ChampionshipSeason getCurrentSeason() {
        int currentYear = instantSource.instant().atZone(LONDON_ZONE).getYear();
        return getSeasonForYear(currentYear).orElseThrow(() -> {
            LOGGER.warn("No championship season set up for current year=[{}]", currentYear);
            return new ChampionshipSeasonException(NO_CURRENT_SEASON);
        });
    }

    @Transactional
    public Optional<ChampionshipSeason> getSeasonForYear(int year) {
        return championshipSeasonPersistenceService.getChampionshipSeason(String.valueOf(year));
    }

    @Transactional
    public List<ChampionshipSeason> getAllSeasons() {
        return championshipSeasonPersistenceService.selectAllSeasons();
    }

    @Transactional
    public ChampionshipSeason createSeason(int year, String name) {
        UUID championshipSeasonUid = UUID.randomUUID();
        String championshipYear = String.valueOf(year);
        if (!championshipSeasonPersistenceService.createSeason(championshipSeasonUid, championshipYear, name)) {
            LOGGER.info("Championship season already exists for year=[{}]", year);
            throw new ChampionshipSeasonException(SEASON_ALREADY_EXISTS);
        }
        UUID globalLeaderboardUid = UUID.randomUUID();
        championshipLeaderboardPersistenceService.createGlobalLeaderboard(
                globalLeaderboardUid, championshipSeasonUid, year + " Global Championship", instantSource.instant());
        // Teams outlive seasons, so carry every existing team into the new season's standings
        leaderboardEntryPersistenceService.addAllTeamsToLeaderboard(globalLeaderboardUid);
        LOGGER.info("Created championship season=[{}] for year=[{}] with global leaderboard", championshipSeasonUid, year);
        return new ChampionshipSeason(championshipSeasonUid, name, championshipYear);
    }
}
