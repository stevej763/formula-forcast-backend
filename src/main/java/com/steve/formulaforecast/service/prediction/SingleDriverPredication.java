package com.steve.formulaforecast.service.prediction;

import java.util.UUID;

public class SingleDriverPredication {

    private final UUID predictionTypeUid;
    private final UUID driverUid;
    private final UUID raceWeekendUid;
    private final UUID userTeamUid;

    public SingleDriverPredication(UUID predictionTypeUid, UUID driverUid, UUID raceWeekendUid, UUID userTeamUid) {
        this.predictionTypeUid = predictionTypeUid;
        this.driverUid = driverUid;
        this.raceWeekendUid = raceWeekendUid;
        this.userTeamUid = userTeamUid;
    }

    public UUID getPredictionTypeUid() {
        return predictionTypeUid;
    }

    public UUID getDriverUid() {
        return driverUid;
    }

    public UUID getRaceWeekendUid() {
        return raceWeekendUid;
    }

    public UUID getUserTeamUid() {
        return userTeamUid;
    }
}
