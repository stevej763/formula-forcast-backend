package com.steve.formulaforecast.service.raceweekends.model;

import java.time.LocalDate;
import java.util.UUID;

public record PracticeSession(UUID practiceSessionUid, LocalDate sessionDate, int practiceSessionNumber) {
}
