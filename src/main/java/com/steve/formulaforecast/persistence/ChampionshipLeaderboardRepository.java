package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.leaderboard.ChampionshipLeaderboardEntity;
import com.steve.formulaforecast.service.leaderboard.LeaderboardType;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface ChampionshipLeaderboardRepository extends Repository<ChampionshipLeaderboardEntity, Long> {

    @Query("""
            SELECT
                championship_leaderboard.championship_leaderboard_uid,
                championship_leaderboard.championship_leaderboard_name,
                championship_leaderboard.created_at
                championship_season.championship_season_uid,
                championship_season.championship_name,
                account.account_uid,
                account.first_name,
                account.last_name,
                championship_leaderboard.leaderboard_type
            FROM championship_leaderboard
            LEFT JOIN account ON championship_leaderboard.owner_account_id = account.id
            JOIN championship_season ON championship_leaderboard.championship_season_id = championship_season.id
            """)
    Stream<ChampionshipLeaderboardEntity> selectAllLeaderboards();

    @Query("""
            SELECT
                championship_leaderboard.championship_leaderboard_uid,
                championship_leaderboard.championship_leaderboard_name,
                championship_leaderboard.created_at,
                championship_season.championship_season_uid,
                championship_season.championship_name,
                account.account_uid,
                account.first_name,
                account.last_name,
                championship_leaderboard.leaderboard_type
            FROM championship_leaderboard
            LEFT JOIN account ON championship_leaderboard.owner_account_id = account.id
            JOIN championship_season ON championship_leaderboard.championship_season_id = championship_season.id
            WHERE leaderboard_type = 'GLOBAL_DEFAULT'
            AND championship_season.championship_season_uid = :championshipSeasonUid
            """)
    Optional<ChampionshipLeaderboardEntity> selectDefaultGlobalLeaderboardForSeason(UUID championshipSeasonUid);
}
