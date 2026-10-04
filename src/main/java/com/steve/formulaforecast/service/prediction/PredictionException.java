package com.steve.formulaforecast.service.prediction;

public class PredictionException extends RuntimeException {

    public static String RACE_WEEKEND_NOT_FOUND = "RACE_WEEKEND_NOT_FOUND";
    public static String PREDICTIONS_LOCKED = "PREDICTIONS_LOCKED";
    public static String PREDICTION_TYPE_NOT_FOUND = "PREDICTION_TYPE_NOT_FOUND";
    public static String INVALID_PICKS = "INVALID_PICKS";

    public PredictionException(String message) {
        super(message);
    }
}
