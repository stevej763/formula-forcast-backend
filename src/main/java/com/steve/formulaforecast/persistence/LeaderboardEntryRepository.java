package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.leaderboardEntity.LeaderboardEntryEntity;
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

    @Query("""
            SELECT
                championship_leaderboard_entrant_uid,
                user_team.team_uid,
                user_team.team_name,
                user_team.team_colour,
                championship_leaderboard.championship_leaderboard_uid
            FROM championship_leaderboard_entry
            JOIN user_team ON championship_leaderboard_entry.user_team_id = user_team.id
            JOIN championship_leaderboard ON championship_leaderboard_entry.championship_leaderboard_id = championship_leaderboard.id
            WHERE championship_leaderboard.championship_leaderboard_uid = :leaderboardUid
            ORDER BY championship_leaderboard_entry.id ASC
            """)
    Stream<LeaderboardEntryEntity> getTeamsInLeaderboard(UUID leaderboardUid);
}
