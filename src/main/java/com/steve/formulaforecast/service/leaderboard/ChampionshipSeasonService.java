package com.steve.formulaforecast.service.leaderboard;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.InstantSource;
import java.time.ZoneId;

@Service
public class ChampionshipSeasonService {

    private static final ZoneId LONDON_ZONE = ZoneId.of("Europe/London");

    private final ChampionshipSeasonPersistenceService championshipSeasonPersistenceService;
    private final InstantSource instantSource;

    ChampionshipSeasonService(
            ChampionshipSeasonPersistenceService championshipSeasonPersistenceService,
            InstantSource instantSource) {
        this.championshipSeasonPersistenceService = championshipSeasonPersistenceService;
        this.instantSource = instantSource;
    }

    @Transactional
    public ChampionshipSeason getCurrentSeason() {
        int currentYear = instantSource.instant().atZone(LONDON_ZONE).getYear();
        String year = String.valueOf(currentYear);
        return championshipSeasonPersistenceService.getChampionshipSeason(year);
    }
}
