package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.leaderboardentity.LeaderboardEntryEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

public interface LeaderboardEntryRepository extends Repository<LeaderboardEntryEntity, Long> {
    @Modifying
    @Query("""
            INSERT INTO championship_leaderboard_entry(championship_leaderboard_entrant_uid, user_team_id, championship_leaderboard_id, created_at)
            VALUES (
                :leaderboardEntryUid,
                (SELECT user_team.id FROM user_team WHERE user_team.team_uid = :userTeamUid),
                (SELECT championship_leaderboard.id FROM championship_leaderboard WHERE championship_leaderboard.championship_leaderboard_uid = :leaderboardUid),
                :created_at
            )
            """)
    int addTeamToLeaderboard(UUID leaderboardUid, UUID userTeamUid, UUID leaderboardEntryUid, Instant created_at);

    /**
     * Adds every existing team to a leaderboard, e.g. a new season's global leaderboard.
     */
    @Modifying
    @Query("""
            INSERT INTO championship_leaderboard_entry(championship_leaderboard_entrant_uid, user_team_id, championship_leaderboard_id, created_at)
            SELECT gen_random_uuid(), user_team.id, championship_leaderboard.id, :createdAt
            FROM user_team
            CROSS JOIN championship_leaderboard
            WHERE championship_leaderboard.championship_leaderboard_uid = :leaderboardUid
            ON CONFLICT ON CONSTRAINT uk_entrant_user_team_leaderboard_id DO NOTHING
            """)
    int addAllTeamsToLeaderboard(UUID leaderboardUid, Instant createdAt);

    /**
     * Teams in a leaderboard with their points for the leaderboard's season, highest first.
     */
    @Query("""
            SELECT
                championship_leaderboard_entry.championship_leaderboard_entrant_uid,
                user_team.team_uid,
                user_team.team_name,
                user_team.team_colour,
                championship_leaderboard.championship_leaderboard_uid,
                COALESCE(SUM(prediction.points), 0) AS points
            FROM championship_leaderboard_entry
            JOIN user_team ON championship_leaderboard_entry.user_team_id = user_team.id
            JOIN championship_leaderboard ON championship_leaderboard_entry.championship_leaderboard_id = championship_leaderboard.id
            LEFT JOIN (prediction JOIN race_weekend ON prediction.race_weekend_id = race_weekend.id)
                ON prediction.user_team_id = user_team.id
                AND race_weekend.championship_season_id = championship_leaderboard.championship_season_id
            WHERE championship_leaderboard.championship_leaderboard_uid = :leaderboardUid
            GROUP BY championship_leaderboard_entry.id, user_team.id, championship_leaderboard.id
            ORDER BY points DESC, lower(user_team.team_name)
            """)
    Stream<LeaderboardEntryEntity> getTeamsInLeaderboard(UUID leaderboardUid);
}
