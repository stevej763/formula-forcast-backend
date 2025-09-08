package com.steve.formulaforecast.service.raceweekends.model;

import java.time.LocalDate;
import java.util.UUID;

public record Race(UUID raceSessionUid, LocalDate raceStartDate) {
}
