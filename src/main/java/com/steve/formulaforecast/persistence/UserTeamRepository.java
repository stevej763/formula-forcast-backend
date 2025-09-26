package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.userTeam.UserTeamEntity;
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
            INSERT INTO user_team(team_uid, team_name, team_colour, account_uid, created_at)
            VALUES (:userTeamUid, :teamName, :teamColour, :accountUid, :createdAt)
            """)
    int insertTeam(UUID userTeamUid, String teamName, String teamColour, UUID accountUid, Instant createdAt);

    @Query("""
            SELECT user_team.team_uid, user_team.team_name, user_team.team_colour, user_team.account_uid
            FROM user_team
            WHERE user_team.account_uid = :accountUid
            """)
    Optional<UserTeamEntity> getTeam(UUID accountUid);

    @Query("""
            SELECT user_team.team_uid, user_team.team_name, user_team.team_colour, user_team.account_uid
            FROM user_team
            WHERE user_team.team_name = :teamName
            """)
    Optional<UserTeamEntity> findByTeamName(String teamName);


    @Query("""
            SELECT user_team.team_uid, user_team.team_name, user_team.team_colour, user_team.account_uid
            FROM user_team
            """)
    Stream<UserTeamEntity> selectAllTeams();
}
