package com.steve.formulaforecast.api.model.raceweekend;

import com.neovisionaries.i18n.CountryCode;
import com.steve.formulaforecast.service.raceweekends.RaceWeekendPersistenceService;
import com.steve.formulaforecast.service.raceweekends.model.RaceName;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekend;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ActiveRaceWeekendUpdateService.class, ActiveRaceWeekendUpdateServiceTest.FixedInstantConfig.class})
class ActiveRaceWeekendUpdateServiceTest {

    public static final UUID RACE_WEEKEND_UID = UUID.randomUUID();

    static final Instant FIXED_INSTANT = Instant.parse("2025-09-28T00:00:00Z");

    @TestConfiguration
    static class FixedInstantConfig {
        @Bean
        @Primary
        InstantSource instantSource() {
            return InstantSource.fixed(FIXED_INSTANT);
        }
    }

    @Autowired
    private InstantSource instantSource;

    @Autowired
    private ActiveRaceWeekendUpdateService underTest;

    @MockitoBean
    private RaceWeekendPersistenceService raceWeekendPersistenceService;

    @Test
    void shouldNotDoAnythingWhenNoLiveActiveOrUpcomingRaceWeekends() {
        when(raceWeekendPersistenceService.getLiveRaceWeekend()).thenReturn(Optional.empty());
        when(raceWeekendPersistenceService.getCurrentRaceWeekend()).thenReturn(Optional.empty());
        when(raceWeekendPersistenceService.getNextRaceWeekend()).thenReturn(Optional.empty());

        underTest.updateRaceWeekendStatus();

        verify(raceWeekendPersistenceService, never()).updateRaceWeekendStatus(any(), any());
    }

    @Test
    void shouldUpdateRaceWeekendToCurrentRaceWeekendWhenStartDateIsWithinFourDays() {
        LocalDate startDate = LocalDate.of(2025, 9, 4);
        LocalDate endDate = LocalDate.of(2025, 9, 5);
        RaceWeekend raceWeekend = aRaceWeekend(startDate, endDate);
        when(raceWeekendPersistenceService.getLiveRaceWeekend()).thenReturn(Optional.empty());
        when(raceWeekendPersistenceService.getCurrentRaceWeekend()).thenReturn(Optional.empty());
        when(raceWeekendPersistenceService.getNextRaceWeekend()).thenReturn(Optional.of(raceWeekend));

        underTest.updateRaceWeekendStatus();

        verify(raceWeekendPersistenceService).updateRaceWeekendStatus(RACE_WEEKEND_UID, RaceWeekendState.RACE_WEEK);
    }

    private RaceWeekend aRaceWeekend(LocalDate raceWeekendStartDate, LocalDate raceWeekendEndDate) {
        return new RaceWeekend(
                RACE_WEEKEND_UID,
                RaceName.AUSTRALIA,
                CountryCode.AU,
                Collections.emptyList(),
                null,
                null,
                null,
                raceWeekendStartDate,
                raceWeekendEndDate,
                new RaceWeekendStatus(RaceWeekendState.UPCOMING, instantSource.instant())
        );
    }

}