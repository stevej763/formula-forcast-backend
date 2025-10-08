package com.steve.formulaforecast.api.race.model.raceweekend;

import org.jspecify.annotations.NonNull;

import java.util.List;

public record RaceWeekendsResponse(@NonNull List<RaceWeekendResponse> raceWeekendResponses) {
}
