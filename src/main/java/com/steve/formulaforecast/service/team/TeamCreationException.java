package com.steve.formulaforecast.service.team;

public class TeamCreationException extends RuntimeException {

    public static String USER_ALREADY_HAS_A_TEAM = "USER_ALREADY_HAS_A_TEAM";
    public static String TEAM_NAME_ALREADY_EXISTS = "TEAM_NAME_ALREADY_EXISTS";

    public TeamCreationException(String message) {
        super(message);
    }
}
