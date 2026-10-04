package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.userteam.UserTeamEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface UserTeamRepository extends Repository<UserTeamEntity, Long> {

    @Modifying
    @Query("""
            INSERT INTO user_team(team_uid, team_name, team_colour, account_id, created_at)
            VALUES (:userTeamUid, :teamName, :teamColour, (SELECT id FROM account WHERE account_uid = :accountUid), :createdAt)
            """)
    int insertTeam(UUID userTeamUid, String teamName, String teamColour, UUID accountUid, Instant createdAt);

    @Query("""
            SELECT user_team.team_uid, user_team.team_name, user_team.team_colour, account.account_uid
            FROM user_team
            JOIN account ON user_team.account_id = account.id
            WHERE account.account_uid = :accountUid
            """)
    Optional<UserTeamEntity> getTeam(UUID accountUid);

    /**
     * Finds a team by name ignoring case, matching the case-insensitive unique index on team names.
     */
    @Query("""
            SELECT user_team.team_uid, user_team.team_name, user_team.team_colour, account.account_uid
            FROM user_team
            JOIN account ON user_team.account_id = account.id
            WHERE lower(user_team.team_name) = lower(:teamName)
            """)
    Optional<UserTeamEntity> findByTeamName(String teamName);


    @Query("""
            SELECT user_team.team_uid, user_team.team_name, user_team.team_colour, account.account_uid
            FROM user_team
            JOIN account ON user_team.account_id = account.id
            """)
    Stream<UserTeamEntity> selectAllTeams();
}
