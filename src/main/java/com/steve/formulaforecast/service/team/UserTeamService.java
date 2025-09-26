package com.steve.formulaforecast.service.team;

import com.steve.formulaforecast.service.leaderboard.ChampionshipLeaderboardPersistenceService;
import com.steve.formulaforecast.service.leaderboard.LeaderboardService;
import com.steve.formulaforecast.service.team.model.UserTeamCreationRequest;
import com.steve.formulaforecast.service.team.model.UserTeam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.steve.formulaforecast.service.team.TeamCreationException.USER_ALREADY_HAS_A_TEAM;

@Service
public class UserTeamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTeamService.class);

    private final UserTeamPersistenceService userTeamPersistenceService;
    private final LeaderboardService leaderboardService;

    public UserTeamService (UserTeamPersistenceService userTeamPersistenceService, LeaderboardService leaderboardService) {
        this.userTeamPersistenceService = userTeamPersistenceService;
        this.leaderboardService = leaderboardService;
    }

    @Transactional
    public List<UserTeam> getAllTeams() {
        return userTeamPersistenceService.selectAllTeams();
    }

    @Transactional
    public Optional<UserTeam> getCurrentTeamForAccount(UUID accountUid) {
        LOGGER.info("Looking up current users team for accountUid=[{}]", accountUid);
        return userTeamPersistenceService.getTeamForAccount(accountUid);
    }

    @Transactional
    public void createTeam(UUID accountUid, UserTeamCreationRequest userTeamCreationRequest) {
        validateTeamCreation(accountUid, userTeamCreationRequest);
        userTeamPersistenceService.createTeam(accountUid, userTeamCreationRequest);
        leaderboardService.addTeamToGlobalLeaderboard(userTeamCreationRequest.getUserTeamUid());
    }

    private void validateTeamCreation(UUID accountUid, UserTeamCreationRequest userTeamCreationRequest) {
        userTeamPersistenceService.getTeamForAccount(accountUid).ifPresent(team -> {
            throw new TeamCreationException(USER_ALREADY_HAS_A_TEAM);
        });
        if (userTeamPersistenceService.teamNameExists(userTeamCreationRequest.getTeamName())) {
            throw new TeamCreationException(TeamCreationException.TEAM_NAME_ALREADY_EXISTS);
        }

    }
}
