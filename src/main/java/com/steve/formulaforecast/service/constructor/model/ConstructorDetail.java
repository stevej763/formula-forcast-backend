package com.steve.formulaforecast.service.constructor.model;

import java.util.UUID;

public class ConstructorDetail {

    private final UUID constructorUid;
    private final String teamName;
    private final String base;

    public ConstructorDetail(UUID constructorUid, String teamName, String base) {
        this.constructorUid = constructorUid;
        this.teamName = teamName;
        this.base = base;
    }

    public UUID getConstructorUid() {
        return constructorUid;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getBase() {
        return base;
    }
}
