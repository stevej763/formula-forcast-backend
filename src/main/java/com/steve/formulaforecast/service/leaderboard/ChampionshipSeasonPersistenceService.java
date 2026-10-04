package com.steve.formulaforecast.service.leaderboard;

import com.steve.formulaforecast.persistence.entity.championshipseason.ChampionshipSeasonEntity;
import com.steve.formulaforecast.persistence.ChampionshipSeasonStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChampionshipSeasonPersistenceService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipSeasonPersistenceService.class);
    private final ChampionshipSeasonStatements championshipSeasonStatements;

    public ChampionshipSeasonPersistenceService(ChampionshipSeasonStatements championshipSeasonStatements) {
        this.championshipSeasonStatements = championshipSeasonStatements;
    }

    public Optional<ChampionshipSeason> getChampionshipSeason(String year) {
        return championshipSeasonStatements.getChampionshipSeason(year)
                .map(this::toModel);
    }

    public boolean createSeason(UUID championshipSeasonUid, String year, String name) {
        int inserted = championshipSeasonStatements.insertChampionshipSeason(championshipSeasonUid, year, name);
        LOGGER.info("Inserted championship season year=[{}] rows affected=[{}]", year, inserted);
        return inserted > 0;
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
