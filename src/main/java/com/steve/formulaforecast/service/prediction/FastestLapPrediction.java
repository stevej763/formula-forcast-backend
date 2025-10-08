package com.steve.formulaforecast.service.prediction;

import java.util.UUID;

public class FastestLapPrediction {

    private final UUID predictionTypeUid;
    private final UUID userTeamUid;
    private final UUID raceWeekendUid;
    private final UUID driverUid;

    public FastestLapPrediction(UUID predictionTypeUid, UUID userTeamUid, UUID raceWeekendUid, UUID driverUid) {
        this.predictionTypeUid = predictionTypeUid;
        this.userTeamUid = userTeamUid;
        this.raceWeekendUid = raceWeekendUid;
        this.driverUid = driverUid;
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

    public UUID getDriverUid() {
        return driverUid;
    }
}
