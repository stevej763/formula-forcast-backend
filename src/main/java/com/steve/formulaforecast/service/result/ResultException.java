package com.steve.formulaforecast.service.result;

public class ResultException extends RuntimeException {

    public static String RACE_WEEKEND_NOT_FOUND = "RACE_WEEKEND_NOT_FOUND";
    public static String PREDICTION_TYPE_NOT_FOUND = "PREDICTION_TYPE_NOT_FOUND";
    public static String RESULTS_BEFORE_LOCK = "RESULTS_BEFORE_LOCK";
    public static String INVALID_RESULT = "INVALID_RESULT";

    public ResultException(String message) {
        super(message);
    }
}
