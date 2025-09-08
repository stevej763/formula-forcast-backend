package com.steve.formulaforecast.api.model.raceweekend;

import org.jspecify.annotations.NonNull;

import java.util.List;

public record RaceWeekendsResponse(@NonNull List<RaceWeekendResponse> raceWeekendResponses) {
}
