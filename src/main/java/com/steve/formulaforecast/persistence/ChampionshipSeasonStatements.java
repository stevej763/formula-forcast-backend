package com.steve.formulaforecast.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface ChampionshipSeasonStatements extends Repository<ChampionshipSeasonEntity, Long>  {

    @Query(
            """
            SELECT
                championship_season_uid,
                championship_name,
                championship_year
            FROM championship_season
            WHERE championship_year = :year
            """
    )
    Optional<ChampionshipSeasonEntity> getChampionshipSeason(String year);
}
