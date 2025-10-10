package com.steve.formulaforecast.service.prediction;

import com.steve.formulaforecast.api.prediction.model.prediction.RankedDriverPrediction;

import java.util.List;
import java.util.UUID;

public class DriverPrediction {

    private final UUID predictionTypeUid;
    private final UUID userTeamUid;
    private final UUID raceWeekendUid;
    private final List<RankedDriverPrediction> rankedDriverPredictions;

    public DriverPrediction(UUID predictionTypeUid, UUID userTeamUid, UUID raceWeekendUid, List<RankedDriverPrediction> rankedDriverPredictions) {
        this.predictionTypeUid = predictionTypeUid;
        this.userTeamUid = userTeamUid;
        this.raceWeekendUid = raceWeekendUid;
        this.rankedDriverPredictions = rankedDriverPredictions;
    }

    public UUID getPredictionTypeUid() {
        return predictionTypeUid;
    }

    public UUID getUserTeamUid() {
        return userTeamUid;
    }

    public UUID getRaceWeekendUid() {
        return raceWeekendUid;
    }

    public List<RankedDriverPrediction> getRankedDriverPredictions() {
        return rankedDriverPredictions;
    }
}
