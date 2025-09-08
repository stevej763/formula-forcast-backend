package com.steve.formulaforecast.service.raceweekends.model;

import java.time.Instant;

public class RaceWeekendStatus {

    private final RaceWeekendState raceWeekendState;
    private final Instant eventTime;

    public RaceWeekendStatus(RaceWeekendState raceWeekendState, Instant eventTime) {
        this.raceWeekendState = raceWeekendState;
        this.eventTime = eventTime;
    }

    public RaceWeekendState getRaceWeekendState() {
        return raceWeekendState;
    }

    public Instant getEventTime() {
        return eventTime;
    }
}
