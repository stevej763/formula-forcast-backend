package com.steve.formulaforecast.service.prediction;

import java.time.Instant;
import java.util.UUID;

public class PredictionTypeDetail {

    private final UUID predictionTypeUid;
    private final PredictionType predictionType;
    private final String description;
    private final PredictionSelectionType predictionSelectionType;
    private final int selectionCount;
    private final Instant createdAt;

    public PredictionTypeDetail(
            UUID predictionTypeUid,
            PredictionType predictionType,
            String description,
            PredictionSelectionType predictionSelectionType,
            int selectionCount,
            Instant createdAt) {
        this.predictionTypeUid = predictionTypeUid;
        this.predictionType = predictionType;
        this.description = description;
        this.predictionSelectionType = predictionSelectionType;
        this.selectionCount = selectionCount;
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

    /**
     * How many ranked picks a prediction of this type takes, e.g. 3 for a top three.
     */
    public int getSelectionCount() {
        return selectionCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
