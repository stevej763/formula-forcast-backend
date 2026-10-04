package com.steve.formulaforecast.api.race.model.raceweekend;

import java.time.Instant;
import java.time.LocalDate;

public record RaceWeekendCreationRequest(
        String raceName,
        String raceLocation,
        LocalDate raceWeekendStartDate,
        LocalDate raceWeekendEndDate,
        Instant qualifyingStartsAt,
        Instant raceStartsAt
) {
}
