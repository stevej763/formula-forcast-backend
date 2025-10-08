package com.steve.formulaforecast.api.race.model.raceweekend;

import java.time.LocalDate;
import java.util.UUID;

public record PracticeSessionResponse(UUID practiceSessionUid, int sessionNumber, LocalDate sessionDate) {
}
