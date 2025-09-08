package com.steve.formulaforecast.persistence.entity.raceweekend;

import com.neovisionaries.i18n.CountryCode;
import com.steve.formulaforecast.service.raceweekends.model.RaceName;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;


public record RaceWeekendEntity(
        UUID raceWeekendUid,
        LocalDate raceWeekendStartDate,
        LocalDate raceWeekendEndDate,
        RaceName raceName,
        CountryCode raceLocation,
        RaceWeekendState status,
        Instant eventTime
) {

}
