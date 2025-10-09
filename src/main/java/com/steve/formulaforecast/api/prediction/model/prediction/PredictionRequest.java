package com.steve.formulaforecast.api.prediction.model.prediction;

import java.util.UUID;

public record PredictionRequest(
        UUID userTeamUid,
        UUID raceWeekendUid,
        UUID predictionTypeUid,
        UUID driverUid) {
}
