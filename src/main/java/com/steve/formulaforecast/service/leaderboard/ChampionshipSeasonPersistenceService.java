package com.steve.formulaforecast.service.leaderboard;

import com.steve.formulaforecast.persistence.ChampionshipSeasonEntity;
import com.steve.formulaforecast.persistence.ChampionshipSeasonStatements;
import org.springframework.stereotype.Service;

@Service
public class ChampionshipSeasonPersistenceService {

    private final ChampionshipSeasonStatements championshipSeasonStatements;

    public ChampionshipSeasonPersistenceService(ChampionshipSeasonStatements championshipSeasonStatements) {
        this.championshipSeasonStatements = championshipSeasonStatements;
    }

    public ChampionshipSeason getChampionshipSeason(String year) {
        return championshipSeasonStatements.getChampionshipSeason(year)
                .map(this::toModel)
                .orElseThrow(() -> new IllegalStateException("No championship season found"));
    }

    private ChampionshipSeason toModel(ChampionshipSeasonEntity entity) {
        return new ChampionshipSeason(entity.championshipSeasonUid(), entity.championshipSeasonName(), entity.championshipSeasonYear());
    }
}
