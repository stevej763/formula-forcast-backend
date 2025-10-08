package com.steve.formulaforecast.api.model.prediction;

import java.util.UUID;

public record FastestLapPredictionRequest(
        UUID userTeamUid,
        UUID raceWeekendUid,
        UUID predictionTypeUid,
        UUID driverUid) {
}
