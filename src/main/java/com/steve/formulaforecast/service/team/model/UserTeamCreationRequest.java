package com.steve.formulaforecast.service.team.model;

import java.util.UUID;

public class UserTeamCreationRequest {

    private final UUID userTeamUid;
    private final String teamName;
    private final String teamColour;

    public UserTeamCreationRequest(UUID userTeamUid, String teamName, String teamColour) {
        this.userTeamUid = userTeamUid;
        this.teamName = teamName;
        this.teamColour = teamColour;
    }

    public UUID getUserTeamUid() {
        return userTeamUid;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamColour() {
        return teamColour;
    }
}
