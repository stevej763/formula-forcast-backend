package com.steve.formulaforecast.api.race.model.raceweekend;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RaceWeekendResponse(
        UUID raceWeekendUid,
        int roundNumber,
        String raceName,
        String raceLocation,
        List<PracticeSessionResponse> practiceSessions,
        QualifyingResponse qualifying,
        SprintResponse sprintResponse,
        RaceResponse raceResponse,
        LocalDate raceWeekendStartDate,
        LocalDate raceWeekendEndDate,
        String raceWeekendStatus,
        Instant raceWeekendStatusTimestamp,
        Instant predictionsLockAt) {
}
