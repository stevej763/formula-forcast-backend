package com.steve.formulaforecast.api.prediction.model.prediction;

import java.util.List;
import java.util.UUID;

public record PredictionRequest(
        UUID userTeamUid,
        UUID raceWeekendUid,
        UUID predictionTypeUid,
        List<DriverPredictionRequestDto> driverPredictions) {
}
