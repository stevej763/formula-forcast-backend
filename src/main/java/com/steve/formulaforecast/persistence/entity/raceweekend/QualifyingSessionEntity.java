package com.steve.formulaforecast.persistence.entity.raceweekend;

import java.time.LocalDate;
import java.util.UUID;

public record QualifyingSessionEntity(UUID qualifyingSessionUid, LocalDate sessionDate) {

}
