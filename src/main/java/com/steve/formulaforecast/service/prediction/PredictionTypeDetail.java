package com.steve.formulaforecast.service.prediction;

import java.time.Instant;
import java.util.UUID;

public class PredictionTypeDetail {

    private final UUID predictionTypeUid;
    private final PredictionType predictionType;
    private final String description;
    private final PredictionSelectionType predictionSelectionType;
    private final Instant createdAt;

    public PredictionTypeDetail(
            UUID predictionTypeUid,
            PredictionType predictionType,
            String description,
            PredictionSelectionType predictionSelectionType,
            Instant createdAt) {
        this.predictionTypeUid = predictionTypeUid;
        this.predictionType = predictionType;
        this.description = description;
        this.predictionSelectionType = predictionSelectionType;
        this.createdAt = createdAt;
    }

    public UUID getPredictionTypeUid() {
        return predictionTypeUid;
    }

    public PredictionType getPredictionType() {
        return predictionType;
    }

    public String getDescription() {
        return description;
    }

    public PredictionSelectionType getPredictionSelectionType() {
        return predictionSelectionType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
