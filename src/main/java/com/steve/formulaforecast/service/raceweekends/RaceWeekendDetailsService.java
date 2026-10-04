package com.steve.formulaforecast.service.raceweekends;

import com.neovisionaries.i18n.CountryCode;
import com.steve.formulaforecast.service.leaderboard.ChampionshipSeason;
import com.steve.formulaforecast.service.leaderboard.ChampionshipSeasonService;
import com.steve.formulaforecast.service.raceweekends.model.RaceName;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.steve.formulaforecast.service.raceweekends.RaceWeekendCreationException.NO_SEASON_FOR_YEAR;

@Service
public class RaceWeekendDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaceWeekendDetailsService.class);

    private final RaceWeekendPersistenceService raceWeekendPersistenceService;
    private final ChampionshipSeasonService championshipSeasonService;

    public RaceWeekendDetailsService(RaceWeekendPersistenceService raceWeekendPersistenceService, ChampionshipSeasonService championshipSeasonService) {
        this.raceWeekendPersistenceService = raceWeekendPersistenceService;
        this.championshipSeasonService = championshipSeasonService;
    }

    @Transactional
    public List<RaceWeekend> getRaceWeekends() {
        return raceWeekendPersistenceService.getAllRaceWeekends();
    }

    @Transactional
    public Optional<RaceWeekend> getRaceWeekend(UUID raceWeekendUid) {
        return raceWeekendPersistenceService.getRaceWeekend(raceWeekendUid);
    }

    @Transactional
    public Optional<RaceWeekend> getRaceCurrentWeekend() {
        return raceWeekendPersistenceService.getCurrentRaceWeekend();
    }

    @Transactional
    public Optional<RaceWeekend> getNextRaceWeekend() {
        return raceWeekendPersistenceService.getNextRaceWeekend();
    }

    @Transactional
    public Optional<RaceWeekend> getLiveRaceWeekend() {
        return raceWeekendPersistenceService.getLiveRaceWeekend();
    }

    @Transactional
    public List<RaceWeekend> getRaceWeekendsForCurrentSeason() {
        ChampionshipSeason season = championshipSeasonService.getCurrentSeason();
        return raceWeekendPersistenceService.getAllRaceWeekendsForSeason(season.getChampionshipSeasonUid());
    }

    /**
     * Creates a race weekend in the season for its start year, numbered into the season by start date.
     */
    @Transactional
    public RaceWeekend createRaceWeekend(
            RaceName raceName,
            CountryCode raceLocation,
            LocalDate raceWeekendStartDate,
            LocalDate raceWeekendEndDate,
            Instant qualifyingStartsAt,
            Instant raceStartsAt) {
        ChampionshipSeason season = championshipSeasonService.getSeasonForYear(raceWeekendStartDate.getYear())
                .orElseThrow(() -> new RaceWeekendCreationException(NO_SEASON_FOR_YEAR));
        UUID raceWeekendUid = UUID.randomUUID();
        raceWeekendPersistenceService.createRaceWeekend(
                raceWeekendUid,
                raceName,
                raceLocation,
                raceWeekendStartDate,
                raceWeekendEndDate,
                qualifyingStartsAt,
                raceStartsAt,
                season.getChampionshipSeasonUid());
        LOGGER.info("Created race weekend=[{}] for [{}] in season=[{}]", raceWeekendUid, raceName, season.getChampionshipYear());
        return raceWeekendPersistenceService.getRaceWeekend(raceWeekendUid).orElseThrow();
    }
}
