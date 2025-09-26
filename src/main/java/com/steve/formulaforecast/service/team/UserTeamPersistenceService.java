package com.steve.formulaforecast.service.team;

import com.steve.formulaforecast.persistence.UserTeamRepository;
import com.steve.formulaforecast.persistence.entity.userTeam.UserTeamEntity;
import com.steve.formulaforecast.service.team.model.UserTeam;
import com.steve.formulaforecast.service.team.model.UserTeamCreationRequest;
import org.springframework.stereotype.Service;

import java.time.InstantSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserTeamPersistenceService {

    private final UserTeamRepository userTeamRepository;
    private final InstantSource instantSource;

    public UserTeamPersistenceService(UserTeamRepository userTeamRepository, InstantSource instantSource) {
        this.userTeamRepository = userTeamRepository;
        this.instantSource = instantSource;
    }

    public Optional<UserTeam> getTeamForAccount(UUID accountUid) {
        return userTeamRepository.getTeam(accountUid).map(this::toModel);
    }

    public boolean teamNameExists(String teamName) {
        return getTeamByName(teamName).isPresent();
    }

    public Optional<UserTeam> getTeamByName(String teamName) {
        return userTeamRepository.findByTeamName(teamName).map(this::toModel);
    }

    public List<UserTeam> selectAllTeams() {
        return userTeamRepository.selectAllTeams().map(this::toModel).toList();
    }

    public void createTeam(UUID accountUid, UserTeamCreationRequest userTeamCreationRequest) {
        userTeamRepository.insertTeam(
                userTeamCreationRequest.getUserTeamUid(),
                userTeamCreationRequest.getTeamName(),
                userTeamCreationRequest.getTeamColour(),
                accountUid,
                instantSource.instant()
                );
    }

    private UserTeam toModel(UserTeamEntity userTeamEntity) {
        return new UserTeam(userTeamEntity.teamUid(), userTeamEntity.teamName(), userTeamEntity.teamColour(), userTeamEntity.accountUid());
    }
}
