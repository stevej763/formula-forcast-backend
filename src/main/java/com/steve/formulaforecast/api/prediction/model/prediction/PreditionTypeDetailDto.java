package com.steve.formulaforecast.api.prediction.model.prediction;

import java.time.Instant;
import java.util.UUID;

public record PreditionTypeDetailDto(
        UUID predictionTypeUid,
        String predictionType,
        String predictionSelectionType,
        String description,
        Instant createdAt
) {
}
