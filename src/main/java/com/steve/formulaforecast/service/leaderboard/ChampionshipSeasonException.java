package com.steve.formulaforecast.service.leaderboard;

public class ChampionshipSeasonException extends RuntimeException {

    public static String NO_CURRENT_SEASON = "NO_CURRENT_SEASON";
    public static String SEASON_ALREADY_EXISTS = "SEASON_ALREADY_EXISTS";

    public ChampionshipSeasonException(String message) {
        super(message);
    }
}
