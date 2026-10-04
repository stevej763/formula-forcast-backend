package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.championshipseason.ChampionshipSeasonEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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

    @Query(
            """
            SELECT
                championship_season_uid,
                championship_name,
                championship_year
            FROM championship_season
            ORDER BY championship_year DESC
            """
    )
    Stream<ChampionshipSeasonEntity> selectAllSeasons();

    @Modifying
    @Query(
            """
            INSERT INTO championship_season (championship_season_uid, championship_year, championship_name)
            VALUES (:championshipSeasonUid, :year, :name)
            ON CONFLICT DO NOTHING
            """
    )
    int insertChampionshipSeason(UUID championshipSeasonUid, String year, String name);
}
