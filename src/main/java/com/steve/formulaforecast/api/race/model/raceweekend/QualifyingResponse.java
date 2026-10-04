package com.steve.formulaforecast.api.race.model.raceweekend;

import java.time.Instant;
import java.util.UUID;

public record QualifyingResponse(Instant startsAt, UUID qualifyingSessionUid) {
}
