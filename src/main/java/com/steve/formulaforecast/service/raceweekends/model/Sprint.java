package com.steve.formulaforecast.service.raceweekends.model;

import java.time.Instant;
import java.util.UUID;

public record Sprint(UUID sprintSessionUid, Instant startsAt) {
}
