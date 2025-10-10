package com.steve.formulaforecast.api.prediction.model.prediction;

import com.steve.formulaforecast.service.prediction.DriverPrediction;

import java.util.Map;
import java.util.UUID;

public record PredictionResponse(Map<UUID, DriverPrediction> predictions) {
}
