package com.steve.formulaforecast.persistence.entity.raceweekend;

import com.steve.formulaforecast.service.raceweekends.model.SessionType;

import java.time.Instant;
import java.util.UUID;

public record RaceWeekendSessionEntity(UUID raceWeekendSessionUid, SessionType sessionType, Instant startsAt) {
}
