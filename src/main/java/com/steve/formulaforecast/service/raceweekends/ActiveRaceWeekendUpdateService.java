package com.steve.formulaforecast.service.raceweekends;

import com.steve.formulaforecast.service.raceweekends.model.RaceWeekend;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.InstantSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.steve.formulaforecast.TimeZones.LONDON_TIME;
import static com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState.COMPLETE;
import static com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState.LIVE;
import static com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState.RACE_WEEK;
import static com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState.UPCOMING;

@Service
public class ActiveRaceWeekendUpdateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveRaceWeekendUpdateService.class);
    private static final int RACE_WEEK_DAYS_BEFORE_START = 4;

    private final RaceWeekendPersistenceService raceWeekendPersistenceService;
    private final InstantSource instantSource;

    ActiveRaceWeekendUpdateService(RaceWeekendPersistenceService raceWeekendPersistenceService, InstantSource instantSource) {
        this.raceWeekendPersistenceService = raceWeekendPersistenceService;
        this.instantSource = instantSource;
    }

    /**
     * Reconciles every incomplete race weekend to the state its dates say it should be in.
     * Only the earliest incomplete weekend may be RACE_WEEK or LIVE (enforced by unique indexes),
     * so demotions are written before promotions.
     */
    @Transactional
    public void updateRaceWeekendStatus() {
        LocalDate today = instantSource.instant().atZone(LONDON_TIME).toLocalDate();
        List<RaceWeekend> incompleteRaceWeekends = raceWeekendPersistenceService.getIncompleteRaceWeekends();

        List<StateChange> demotions = new ArrayList<>();
        List<StateChange> promotions = new ArrayList<>();
        boolean activeRaceWeekendAssigned = false;

        for (RaceWeekend raceWeekend : incompleteRaceWeekends) {
            RaceWeekendState targetState = targetState(raceWeekend, today);
            if (isActive(targetState)) {
                if (activeRaceWeekendAssigned) {
                    targetState = UPCOMING;
                }
                activeRaceWeekendAssigned = true;
            }

            RaceWeekendState currentState = raceWeekend.getRaceWeekendStatus().getRaceWeekendState();
            if (targetState != currentState) {
                StateChange stateChange = new StateChange(raceWeekend, currentState, targetState);
                (isActive(targetState) ? promotions : demotions).add(stateChange);
            }
        }

        demotions.forEach(this::apply);
        promotions.forEach(this::apply);

        if (demotions.isEmpty() && promotions.isEmpty()) {
            LOGGER.debug("Race weekend states are up to date for [{}]", today);
        }
    }

    private RaceWeekendState targetState(RaceWeekend raceWeekend, LocalDate today) {
        if (today.isAfter(raceWeekend.getRaceWeekendEndDate())) {
            return COMPLETE;
        }
        if (!today.isBefore(raceWeekend.getRaceWeekendStartDate())) {
            return LIVE;
        }
        if (!today.isBefore(raceWeekend.getRaceWeekendStartDate().minusDays(RACE_WEEK_DAYS_BEFORE_START))) {
            return RACE_WEEK;
        }
        return UPCOMING;
    }

    private boolean isActive(RaceWeekendState raceWeekendState) {
        return raceWeekendState == RACE_WEEK || raceWeekendState == LIVE;
    }

    private void apply(StateChange stateChange) {
        LOGGER.info("Updating race weekend [{}] starting [{}] from [{}] to [{}]",
                stateChange.raceWeekend().getRaceName(),
                stateChange.raceWeekend().getRaceWeekendStartDate(),
                stateChange.from(),
                stateChange.to());
        raceWeekendPersistenceService.updateRaceWeekendStatus(stateChange.raceWeekend().getRaceWeekendUid(), stateChange.to());
    }

    private record StateChange(RaceWeekend raceWeekend, RaceWeekendState from, RaceWeekendState to) {
    }
}
