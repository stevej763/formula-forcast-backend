package com.steve.formulaforecast.persistence;

import java.util.UUID;

public record DriverPredictionEntity(UUID predictionChoiceUid, UUID predictionUid, UUID driverUid, Long rank) {
}
