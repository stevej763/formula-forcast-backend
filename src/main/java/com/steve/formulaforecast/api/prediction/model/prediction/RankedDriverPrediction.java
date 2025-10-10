package com.steve.formulaforecast.api.prediction.model.prediction;

import java.util.UUID;

public class RankedDriverPrediction {

    private final UUID driverUid;
    private final int rank;

    public RankedDriverPrediction(UUID driverUid, int rank) {
        this.driverUid = driverUid;
        this.rank = rank;
    }

    public UUID getDriverUid() {
        return driverUid;
    }

    public int getRank() {
        return rank;
    }
}
