package com.steve.formulaforecast.api.model.raceweekend;

import com.steve.formulaforecast.service.raceweekends.RaceWeekendPersistenceService;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekend;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalDate;
import java.util.Optional;

import static com.steve.formulaforecast.TimeZones.LONDON_TIME;

@Service
public class ActiveRaceWeekendUpdateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveRaceWeekendUpdateService.class);

    private final RaceWeekendPersistenceService raceWeekendPersistenceService;
    private final InstantSource instantSource;

    ActiveRaceWeekendUpdateService(RaceWeekendPersistenceService raceWeekendPersistenceService, InstantSource instantSource) {
        this.raceWeekendPersistenceService = raceWeekendPersistenceService;
        this.instantSource = instantSource;
    }

    public void updateRaceWeekendStatus() {
        // Check on the current race weekend. If there is one, check if it is the start of the weekend and set it live
        Optional<RaceWeekend> currentRaceWeekend = raceWeekendPersistenceService.getCurrentRaceWeekend();
        if (currentRaceWeekend.isPresent()) {
            LOGGER.info("Found current race weekend, checking if it should be updated to LIVE");
            updateCurrentRaceWeekend(currentRaceWeekend.get());
            return;
        }

        // Check on the LIVE race weekend. If there is one, check if it has now finished and set it completed
        Optional<RaceWeekend> liveRaceWeekend = raceWeekendPersistenceService.getLiveRaceWeekend();
        if (liveRaceWeekend.isPresent()) {
            LOGGER.info("Found LIVE race weekend [{}] checking if it has now finished", liveRaceWeekend.get().getRaceName());
            updateLiveRaceWeekend(liveRaceWeekend.get());
        } else {
            checkForNextRaceWeekend();
        }
    }


    private void updateLiveRaceWeekend(RaceWeekend raceWeekend) {
        LocalDate currentDate = getCurrentDate();
        if (currentDate.isAfter(raceWeekend.getRaceWeekendEndDate())) {
            LOGGER.info("The LIVE race weekend [{}] is now over! Updating to completed", raceWeekend.getRaceName());
            raceWeekendPersistenceService.updateRaceWeekendStatus(raceWeekend.getRaceWeekendUid(), RaceWeekendState.COMPLETE);
            checkForNextRaceWeekend();
        } else {
            LOGGER.info("The LIVE race weekend [{}] is still ongoing. No action taken.", raceWeekend.getRaceName());
        }
    }

    private void checkForNextRaceWeekend() {
        LOGGER.info("Checking if the next upcoming weekend is this week");
        // Check on the upcoming race weekend. If there is one, check if it is within 4 days and set it to RACE_WEEK
        Optional<RaceWeekend> nextRaceWeekend = raceWeekendPersistenceService.getNextRaceWeekend();
        nextRaceWeekend.ifPresentOrElse(this::updateNextRaceWeekendToCurrentWhenRaceWeek,
                () -> LOGGER.info("No upcoming race weekends. Championship may have ended and the next season has not been set up yet."));
    }

    private void updateCurrentRaceWeekend(RaceWeekend raceWeekend) {
        LocalDate currentDate = getCurrentDate();
        if (raceWeekend.getRaceWeekendStartDate().equals(currentDate)) {
            LOGGER.info("It is the start of the race weekend! Updating current race weekend to LIVE status for [{}]", raceWeekend.getRaceName());
            raceWeekendPersistenceService.updateRaceWeekendStatus(raceWeekend.getRaceWeekendUid(), RaceWeekendState.LIVE);
        } else {
            LOGGER.info("The current race weekend starts on [{}] not updating to LIVE yet. No action taken.", raceWeekend.getRaceWeekendStartDate());
        }
    }

    private void updateNextRaceWeekendToCurrentWhenRaceWeek(RaceWeekend raceWeekend) {
        setRaceWeekendToCurrentRaceWeekend(raceWeekend);
    }

    private void setRaceWeekendToCurrentRaceWeekend(RaceWeekend raceWeekend) {
        Instant now = instantSource.instant();
        boolean isRaceWeekForUpcomingRace = daysBetween(now, raceWeekend) < 4;
        if (isRaceWeekForUpcomingRace) {
            LOGGER.info("Is within 4 days of next upcoming race weekend [{}]. Its race week!", raceWeekend.getRaceName());
            raceWeekendPersistenceService.updateRaceWeekendStatus(raceWeekend.getRaceWeekendUid(), RaceWeekendState.RACE_WEEK);
        } else {
            LOGGER.info("Upcoming race weekend [{}] is not this week startDate=[{}]. No action taken.", raceWeekend.getRaceName(), raceWeekend.getRaceWeekendStartDate());
        }
    }

    private long daysBetween(Instant now, RaceWeekend raceWeekend) {
        return Duration.between(now, getRaceWeekendStartDate(raceWeekend)).toDays();
    }

    private Instant getRaceWeekendStartDate(RaceWeekend raceWeekend) {
        return raceWeekend.getRaceWeekendStartDate().atStartOfDay().atZone(LONDON_TIME).toInstant();
    }

    private LocalDate getCurrentDate() {
        return instantSource.instant().atZone(LONDON_TIME).toLocalDate();
    }
}
