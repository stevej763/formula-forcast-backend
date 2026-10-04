package com.steve.formulaforecast.api.race.model.raceweekend;

import java.time.Instant;
import java.util.UUID;

public record SprintResponse(UUID sprintSessionUid, Instant startsAt) {
}
