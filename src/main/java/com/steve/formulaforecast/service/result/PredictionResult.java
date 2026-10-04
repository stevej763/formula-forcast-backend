package com.steve.formulaforecast.service.result;

import com.steve.formulaforecast.api.prediction.model.prediction.RankedDriverPrediction;

import java.util.List;
import java.util.UUID;

/**
 * The actual outcome of one prediction type for one race weekend. Several drivers share rank 1 when a single driver result is a tie.
 */
public record PredictionResult(UUID raceWeekendUid, UUID predictionTypeUid, List<RankedDriverPrediction> drivers) {
}
