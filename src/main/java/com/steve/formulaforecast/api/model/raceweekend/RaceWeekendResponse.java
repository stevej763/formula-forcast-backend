package com.steve.formulaforecast.api.model.raceweekend;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record RaceWeekendResponse(
        String raceName,
        String raceLocation,
        List<PracticeSessionResponse> practiceSessions,
        QualifyingResponse qualifying,
        SprintResponse sprintResponse,
        RaceResponse raceResponse,
        LocalDate raceWeekendStartDate,
        LocalDate raceWeekendEndDate,
        String raceWeekendStatus,
        Instant raceWeekendStatusTimestamp) {
}
