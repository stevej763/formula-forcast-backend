package com.steve.formulaforecast.service.raceweekends;

import com.steve.formulaforecast.persistence.*;
import com.steve.formulaforecast.persistence.entity.raceweekend.*;
import com.steve.formulaforecast.service.raceweekends.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neovisionaries.i18n.CountryCode;
import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RaceWeekendPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaceWeekendPersistenceService.class);
    private final RaceWeekendRepository raceWeekendRepository;
    private final RaceWeekendSessionRepository raceWeekendSessionRepository;
    private final InstantSource instantSource;

    RaceWeekendPersistenceService(
            RaceWeekendRepository raceWeekendRepository,
            RaceWeekendSessionRepository raceWeekendSessionRepository,
            InstantSource instantSource) {
        this.raceWeekendRepository = raceWeekendRepository;
        this.raceWeekendSessionRepository = raceWeekendSessionRepository;
        this.instantSource = instantSource;
    }

    @Transactional
    public List<RaceWeekend> getAllRaceWeekendsForSeason(UUID seasonUid) {
        List<RaceWeekendEntity> raceWeekendEntityStream = raceWeekendRepository.selectAllRaceWeekendsForSeason(seasonUid).toList();
        return raceWeekendEntityStream
                .stream()
                .map(this::mapToModel)
                .toList();

    }

    @Transactional
    public List<RaceWeekend> getAllRaceWeekends() {
        List<RaceWeekendEntity> raceWeekendEntityStream = raceWeekendRepository.selectAllRaceWeekends().toList();
        return raceWeekendEntityStream
                .stream()
                .map(this::mapToModel)
                .toList();
    }

    @Transactional
    public Optional<RaceWeekend> getRaceWeekend(UUID raceWeekend) {
        return raceWeekendRepository.selectRaceWeekends(raceWeekend).map(this::mapToModel);
    }

    @Transactional
    public Optional<RaceWeekend> getLiveRaceWeekend() {
        return raceWeekendRepository.selectLiveRaceWeekend().map(this::mapToModel);
    }

    @Transactional
    public Optional<RaceWeekend> getCurrentRaceWeekend() {
        return raceWeekendRepository.selectCurrentRaceWeekend().map(this::mapToModel);
    }

    @Transactional
    public Optional<RaceWeekend> getNextRaceWeekend() {
        return raceWeekendRepository.selectNextRaceWeekend().map(this::mapToModel);
    }

    @Transactional
    public void createRaceWeekend(
            UUID raceWeekendUid,
            RaceName raceName,
            CountryCode raceLocation,
            LocalDate raceWeekendStartDate,
            LocalDate raceWeekendEndDate,
            Instant qualifyingStartsAt,
            Instant raceStartsAt,
            UUID championshipSeasonUid) {
        raceWeekendRepository.insertRaceWeekend(raceWeekendUid, raceWeekendStartDate, raceWeekendEndDate, raceName.name(), raceLocation.name(), championshipSeasonUid);
        raceWeekendRepository.renumberSeason(championshipSeasonUid);
        raceWeekendSessionRepository.insertSession(UUID.randomUUID(), raceWeekendUid, SessionType.QUALIFYING, qualifyingStartsAt);
        raceWeekendSessionRepository.insertSession(UUID.randomUUID(), raceWeekendUid, SessionType.RACE, raceStartsAt);
        updateRaceWeekendStatus(raceWeekendUid, RaceWeekendState.UPCOMING);
    }

    @Transactional
    public List<RaceWeekend> getIncompleteRaceWeekends() {
        return raceWeekendRepository.selectIncompleteRaceWeekends().toList()
                .stream()
                .map(this::mapToModel)
                .toList();
    }

    @Transactional
    public void updateRaceWeekendStatus(UUID raceWeekendUid, RaceWeekendState raceWeekendState) {
        Instant eventTime = instantSource.instant();
        raceWeekendRepository.updateRaceWeekendCurrentStatus(raceWeekendUid, eventTime, raceWeekendState);
        raceWeekendRepository.updateRaceWeekendStatusHistory(raceWeekendUid, raceWeekendState, eventTime);
    }

    private RaceWeekend mapToModel(RaceWeekendEntity raceWeekendEntity) {
        Map<SessionType, RaceWeekendSessionEntity> sessions = raceWeekendSessionRepository.selectSessionsForRaceWeekend(raceWeekendEntity.raceWeekendUid())
                .collect(Collectors.toMap(RaceWeekendSessionEntity::sessionType, Function.identity()));

        List<PracticeSession> practiceSessions = sessions.values().stream()
                .filter(session -> session.sessionType().isPractice())
                .sorted(Comparator.comparing(RaceWeekendSessionEntity::sessionType))
                .map(session -> new PracticeSession(session.raceWeekendSessionUid(), session.startsAt(), session.sessionType().practiceSessionNumber()))
                .toList();
        Qualifying qualifying = Optional.ofNullable(sessions.get(SessionType.QUALIFYING))
                .map(session -> new Qualifying(session.raceWeekendSessionUid(), session.startsAt()))
                .orElseThrow(() -> new IllegalStateException("Race weekend " + raceWeekendEntity.raceWeekendUid() + " has no qualifying session"));
        Sprint sprint = Optional.ofNullable(sessions.get(SessionType.SPRINT))
                .map(session -> new Sprint(session.raceWeekendSessionUid(), session.startsAt()))
                .orElse(null);
        Race race = Optional.ofNullable(sessions.get(SessionType.RACE))
                .map(session -> new Race(session.raceWeekendSessionUid(), session.startsAt()))
                .orElseThrow(() -> new IllegalStateException("Race weekend " + raceWeekendEntity.raceWeekendUid() + " has no race session"));

        return new RaceWeekend(
                raceWeekendEntity.raceWeekendUid(),
                raceWeekendEntity.roundNumber(),
                raceWeekendEntity.raceName(),
                raceWeekendEntity.raceLocation(),
                practiceSessions,
                qualifying,
                sprint,
                race,
                raceWeekendEntity.raceWeekendStartDate(),
                raceWeekendEntity.raceWeekendEndDate(),
                new RaceWeekendStatus(raceWeekendEntity.status(), raceWeekendEntity.eventTime()));
    }
}
