package com.steve.formulaforecast.service.leaderboard;

import com.steve.formulaforecast.persistence.entity.championshipseason.ChampionshipSeasonEntity;
import com.steve.formulaforecast.persistence.ChampionshipSeasonStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChampionshipSeasonPersistenceService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipSeasonPersistenceService.class);
    private final ChampionshipSeasonStatements championshipSeasonStatements;

    public ChampionshipSeasonPersistenceService(ChampionshipSeasonStatements championshipSeasonStatements) {
        this.championshipSeasonStatements = championshipSeasonStatements;
    }

    public ChampionshipSeason getChampionshipSeason(String year) {
        return championshipSeasonStatements.getChampionshipSeason(year)
                .map(this::toModel)
                .orElseThrow(() -> new IllegalStateException("No championship season found"));
    }

    public List<ChampionshipSeason> selectAllSeasons() {
        return championshipSeasonStatements.selectAllSeasons()
                .filter(championshipSeasonEntity -> {
                    LOGGER.info("championshipSeasonEntity: {}", championshipSeasonEntity);
                    return true;
                })
                .map(this::toModel)
                .toList();
    }

    private ChampionshipSeason toModel(ChampionshipSeasonEntity entity) {
        return new ChampionshipSeason(
                entity.championshipSeasonUid(),
                entity.championshipName(),
                entity.championshipYear());
    }
}
