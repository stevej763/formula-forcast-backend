package com.steve.formulaforecast.service.raceweekends.model;

import com.neovisionaries.i18n.CountryCode;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RaceWeekend {

    private final UUID raceWeekendUid;
    private final int roundNumber;
    private final RaceName raceName;
    private final CountryCode raceLocation;
    private final List<PracticeSession> practiceSessions;
    private final Qualifying qualifying;
    private final Sprint sprint;
    private final Race race;
    private final RaceWeekendStatus raceWeekendStatus;
    private final LocalDate raceWeekendStartDate;
    private final LocalDate raceWeekendEndDate;

    public RaceWeekend(
            UUID raceWeekendUid,
            int roundNumber,
            RaceName raceName,
            CountryCode raceLocation,
            List<PracticeSession> practiceSessions,
            Qualifying qualifying,
            Sprint sprint,
            Race race,
            LocalDate raceWeekendStartDate,
            LocalDate raceWeekendEndDate,
            RaceWeekendStatus raceWeekendStatus) {
        this.raceWeekendUid = raceWeekendUid;
        this.roundNumber = roundNumber;
        this.raceName = raceName;
        this.raceLocation = raceLocation;
        this.practiceSessions = practiceSessions;
        this.qualifying = qualifying;
        this.sprint = sprint;
        this.race = race;
        this.raceWeekendStatus = raceWeekendStatus;
        this.raceWeekendStartDate = raceWeekendStartDate;
        this.raceWeekendEndDate = raceWeekendEndDate;
    }

    public UUID getRaceWeekendUid() {
        return raceWeekendUid;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public RaceName getRaceName() {
        return raceName;
    }

    public CountryCode getRaceLocation() {
        return raceLocation;
    }

    public List<PracticeSession> getPracticeSessions() {
        return practiceSessions;
    }

    public Qualifying getQualifying() {
        return qualifying;
    }

    public Optional<Sprint> getSprint() {
        return Optional.ofNullable(sprint);
    }

    public Race getRace() {
        return race;
    }

    public RaceWeekendStatus getRaceWeekendStatus() {
        return raceWeekendStatus;
    }

    public LocalDate getRaceWeekendStartDate() {
        return raceWeekendStartDate;
    }

    public LocalDate getRaceWeekendEndDate() {
        return raceWeekendEndDate;
    }

    /**
     * Picks for every prediction type lock when qualifying starts.
     */
    public Instant getPredictionsLockAt() {
        return qualifying.startsAt();
    }
}
