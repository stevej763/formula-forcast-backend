package com.steve.formulaforecast.service.raceweekends;

import com.steve.formulaforecast.persistence.*;
import com.steve.formulaforecast.persistence.entity.raceweekend.*;
import com.steve.formulaforecast.service.raceweekends.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RaceWeekendPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaceWeekendPersistenceService.class);
    private final RaceWeekendRepository raceWeekendRepository;
    private final RaceWeekendPracticeRepository raceWeekendPracticeRepository;
    private final RaceWeekendQualifyingRepository raceWeekendQualifyingRepository;
    private final RaceWeekendRaceRepository raceWeekendRaceRepository;
    private final RaceWeekendSprintRepository raceWeekendSprintRepository;

    RaceWeekendPersistenceService(
            RaceWeekendRepository raceWeekendRepository,
            RaceWeekendPracticeRepository raceWeekendPracticeRepository,
            RaceWeekendQualifyingRepository raceWeekendQualifyingRepository,
            RaceWeekendRaceRepository raceWeekendRaceRepository,
            RaceWeekendSprintRepository raceWeekendSprintRepository) {
        this.raceWeekendRepository = raceWeekendRepository;
        this.raceWeekendPracticeRepository = raceWeekendPracticeRepository;
        this.raceWeekendQualifyingRepository = raceWeekendQualifyingRepository;
        this.raceWeekendRaceRepository = raceWeekendRaceRepository;
        this.raceWeekendSprintRepository = raceWeekendSprintRepository;
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
    public Optional<RaceWeekend> getCurrentRaceWeekend() {
        return raceWeekendRepository.selectCurrentRaceWeekend().map(this::mapToModel);
    }

    @Transactional
    public Optional<RaceWeekend> getNextRaceWeekend() {
        return raceWeekendRepository.selectNextRaceWeekend().map(this::mapToModel);
    }

    private RaceWeekend mapToModel(RaceWeekendEntity raceWeekendEntity) {
        List<PracticeSession> practiceSessions = raceWeekendPracticeRepository.selectPracticeSessionsForRaceWeekend(raceWeekendEntity.raceWeekendUid()).map(this::toPracticeSession).toList();

        Qualifying qualifying = raceWeekendQualifyingRepository.selectQualifyingSessionForRaceWeekend(raceWeekendEntity.raceWeekendUid()).map(this::toQualiSession).orElseThrow();

        Optional<Sprint> sprint = raceWeekendSprintRepository.selectSprintSessionForRaceWeekend(raceWeekendEntity.raceWeekendUid()).map(this::toSprintSession);

        Race race = raceWeekendRaceRepository.selectRaceSessionForRaceWeekend(raceWeekendEntity.raceWeekendUid()).map(this::toRaceModel). orElseThrow();
        return new RaceWeekend(
                raceWeekendEntity.raceWeekendUid(),
                raceWeekendEntity.raceName(),
                raceWeekendEntity.raceLocation(),
                practiceSessions,
                qualifying,
                sprint.orElse(null),
                race,
                raceWeekendEntity.raceWeekendStartDate(),
                raceWeekendEntity.raceWeekendEndDate(),
                new RaceWeekendStatus(raceWeekendEntity.status(), raceWeekendEntity.eventTime()));
    }

    private Sprint toSprintSession(SprintSessionEntity sprintSessionEntity) {
        return new Sprint(sprintSessionEntity.sprintSessionUid(), sprintSessionEntity.sessionDate());
    }

    private Race toRaceModel(RaceSessionEntity raceSessionEntity) {
        return new Race(raceSessionEntity.raceSessionUid(), raceSessionEntity.sessionDate());
    }

    private Qualifying toQualiSession(QualifyingSessionEntity qualifyingSessionEntity) {
        return new Qualifying(qualifyingSessionEntity.qualifyingSessionUid(), qualifyingSessionEntity.sessionDate());
    }

    private PracticeSession toPracticeSession(PracticeSessionEntity practiceSessionEntity) {
        return new PracticeSession(practiceSessionEntity.practiceSessionUid(), practiceSessionEntity.sessionDate(), practiceSessionEntity.practiceSessionNumber());
    }
}
