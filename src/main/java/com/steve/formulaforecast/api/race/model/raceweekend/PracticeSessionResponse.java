package com.steve.formulaforecast.api.race.model.raceweekend;

import java.time.Instant;
import java.util.UUID;

public record PracticeSessionResponse(UUID practiceSessionUid, int sessionNumber, Instant startsAt) {
}
