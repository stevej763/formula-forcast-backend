package com.steve.formulaforecast.service.team.model;

import java.util.UUID;

public class UserTeam {
    private final UUID teamUid;
    private final String teamName;
    private final String teamColour;
    private final UUID accountUid;


    public UserTeam(UUID teamUid, String teamName, String teamColour, UUID accountUid) {
        this.teamUid = teamUid;
        this.teamName = teamName;
        this.teamColour = teamColour;
        this.accountUid = accountUid;
    }

    public UUID getTeamUid() {
        return teamUid;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamColour() {
        return teamColour;
    }

    public UUID getAccountUid() {
        return accountUid;
    }
}
