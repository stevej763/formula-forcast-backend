package com.steve.formulaforecast.persistence.entity.prediction;

import com.steve.formulaforecast.service.prediction.PredictionSelectionType;
import com.steve.formulaforecast.service.prediction.PredictionType;

import java.time.Instant;
import java.util.UUID;

public record PredictionTypeEntity(
        UUID predictionTypeUid,
        PredictionType predictionType,
        String description,
        PredictionSelectionType predictionSelectionType,
        Instant createdAt
) {

}
