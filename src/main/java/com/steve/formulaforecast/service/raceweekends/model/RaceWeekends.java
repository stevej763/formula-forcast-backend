package com.steve.formulaforecast.service.raceweekends.model;

import org.jspecify.annotations.NonNull;

import java.util.List;

public record RaceWeekends(@NonNull List<RaceWeekend> raceWeekendResponses) {
}
