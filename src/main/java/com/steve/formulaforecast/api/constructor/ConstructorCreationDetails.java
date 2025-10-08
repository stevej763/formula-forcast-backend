package com.steve.formulaforecast.api.constructor;

public class ConstructorCreationDetails {

    private final String teamName;
    private final String base;

    public ConstructorCreationDetails(String teamName, String base) {
        this.teamName = teamName;
        this.base = base;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getBase() {
        return base;
    }
}
