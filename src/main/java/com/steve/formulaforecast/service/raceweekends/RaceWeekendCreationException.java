package com.steve.formulaforecast.service.raceweekends;

public class RaceWeekendCreationException extends RuntimeException {

    public static String NO_SEASON_FOR_YEAR = "NO_SEASON_FOR_YEAR";

    public RaceWeekendCreationException(String message) {
        super(message);
    }
}
