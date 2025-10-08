package com.steve.formulaforecast.service.leaderboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.InstantSource;
import java.time.ZoneId;
import java.util.List;

@Service
public class ChampionshipSeasonService {



    private static final ZoneId LONDON_ZONE = ZoneId.of("Europe/London");
    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipSeasonService.class);

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

    @Transactional
    public List<ChampionshipSeason> getAllSeasons() {
        return championshipSeasonPersistenceService.selectAllSeasons();
    }
}
