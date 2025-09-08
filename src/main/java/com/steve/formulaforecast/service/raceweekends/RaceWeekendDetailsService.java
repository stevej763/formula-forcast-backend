package com.steve.formulaforecast.service.raceweekends;

import com.neovisionaries.i18n.CountryCode;
import com.steve.formulaforecast.service.raceweekends.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RaceWeekendDetailsService {

    private final RaceWeekendPersistenceService raceWeekendPersistenceService;

    public RaceWeekendDetailsService(RaceWeekendPersistenceService raceWeekendPersistenceService) {
        this.raceWeekendPersistenceService = raceWeekendPersistenceService;
    }

    @Transactional
    public List<RaceWeekend> getRaceWeekends() {
        return raceWeekendPersistenceService.getAllRaceWeekends();
    }

    @Transactional
    public Optional<RaceWeekend> getRaceWeekend(UUID raceWeekendUid) {
        return raceWeekendPersistenceService.getRaceWeekend(raceWeekendUid);
    }

    public Optional<RaceWeekend> getRaceCurrentWeekend() {
        return raceWeekendPersistenceService.getCurrentRaceWeekend();
    }

    public Optional<RaceWeekend> getNextRaceWeekend() {
        return raceWeekendPersistenceService.getNextRaceWeekend();
    }
}
