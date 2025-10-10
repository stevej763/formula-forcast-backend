package com.steve.formulaforecast.api.prediction.model.prediction;

import java.util.UUID;

public record DriverPredictionRequestDto(UUID driverUid, int rank) {
}
