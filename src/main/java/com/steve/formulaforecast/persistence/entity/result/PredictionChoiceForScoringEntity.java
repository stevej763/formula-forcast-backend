package com.steve.formulaforecast.persistence.entity.result;

import java.util.UUID;

public record PredictionChoiceForScoringEntity(UUID predictionUid, UUID driverUid, int rank) {
}
