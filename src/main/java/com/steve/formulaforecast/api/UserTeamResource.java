package com.steve.formulaforecast.api;

import com.steve.formulaforecast.api.model.team.TeamDetailsResponse;
import com.steve.formulaforecast.api.model.team.TeamDetailsDto;
import com.steve.formulaforecast.api.model.team.TeamDetailResponse;
import com.steve.formulaforecast.api.model.team.TeamCreationRequestDto;
import com.steve.formulaforecast.service.team.UserTeamService;
import com.steve.formulaforecast.service.Account.model.Account;
import com.steve.formulaforecast.service.authentication.AuthenticatedAccountProvider;
import com.steve.formulaforecast.service.team.model.UserTeam;
import com.steve.formulaforecast.service.team.model.UserTeamCreationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/team", produces = APPLICATION_JSON_VALUE)
public class UserTeamResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTeamResource.class);

    private final AuthenticatedAccountProvider authenticatedAccountProvider;
    private final UserTeamService userTeamService;

    public UserTeamResource(AuthenticatedAccountProvider authenticatedAccountProvider, UserTeamService userTeamService) {
        this.authenticatedAccountProvider = authenticatedAccountProvider;
        this.userTeamService = userTeamService;
    }

    @GetMapping
    public ResponseEntity<TeamDetailResponse> getTeam() {
        LOGGER.info("Getting team");
        Account account = authenticatedAccountProvider.getAuthenticatedAccount();
        Optional<UserTeam> currentTeamForAccount = userTeamService.getCurrentTeamForAccount(account.getAccountUid());
        Optional<TeamDetailsDto> teamDetailsDto = currentTeamForAccount.map(this::toDto);
        return ResponseEntity.ok(new TeamDetailResponse(teamDetailsDto.orElse(null)));
    }
    
    @GetMapping("/all")
    public ResponseEntity<TeamDetailsResponse> getAllTeams() {
        List<TeamDetailsDto> allTeams = userTeamService.getAllTeams().stream().map(this::toDto).toList();
        return ResponseEntity.ok(new TeamDetailsResponse(allTeams));
    }

    @PostMapping("/create")
    public void createTeam(@RequestBody TeamCreationRequestDto teamCreationRequestDto) {
        Account account = authenticatedAccountProvider.getAuthenticatedAccount();
        LOGGER.info("Creating team for accountUid=[{}] with values=[{}]", account.getAccountUid(), teamCreationRequestDto);
        UserTeamCreationRequest userTeamCreationRequest = new UserTeamCreationRequest(UUID.randomUUID(), teamCreationRequestDto.teamName(), teamCreationRequestDto.teamColour());
        userTeamService.createTeam(account.getAccountUid(), userTeamCreationRequest);
    }

    private TeamDetailsDto toDto(UserTeam userTeam) {
        return new TeamDetailsDto(userTeam.getTeamUid(), userTeam.getTeamName(), userTeam.getTeamColour());
    }
}
