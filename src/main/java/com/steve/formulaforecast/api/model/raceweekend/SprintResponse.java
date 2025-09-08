package com.steve.formulaforecast.api.model.raceweekend;

import java.time.LocalDate;
import java.util.UUID;

public record SprintResponse(UUID sprintSessionUid, LocalDate sessionDate) {
}
