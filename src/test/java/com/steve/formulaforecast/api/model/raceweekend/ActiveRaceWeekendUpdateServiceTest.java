package com.steve.formulaforecast.api.model.raceweekend;

import com.neovisionaries.i18n.CountryCode;
import com.steve.formulaforecast.service.raceweekends.ActiveRaceWeekendUpdateService;
import com.steve.formulaforecast.service.raceweekends.RaceWeekendPersistenceService;
import com.steve.formulaforecast.service.raceweekends.model.RaceName;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekend;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState.COMPLETE;
import static com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState.LIVE;
import static com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState.RACE_WEEK;
import static com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState.UPCOMING;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ActiveRaceWeekendUpdateService.class)
class ActiveRaceWeekendUpdateServiceTest {

    // Race weekend runs Friday 10th - Sunday 12th October 2025
    private static final LocalDate START_DATE = LocalDate.of(2025, 10, 10);
    private static final LocalDate END_DATE = LocalDate.of(2025, 10, 12);

    @Autowired
    private ActiveRaceWeekendUpdateService underTest;

    @MockitoBean
    private RaceWeekendPersistenceService raceWeekendPersistenceService;

    @MockitoBean
    private InstantSource instantSource;

    @Test
    void shouldNotDoAnythingWhenNoIncompleteRaceWeekends() {
        givenToday(LocalDate.of(2025, 10, 1));
        when(raceWeekendPersistenceService.getIncompleteRaceWeekends()).thenReturn(List.of());

        underTest.updateRaceWeekendStatus();

        verify(raceWeekendPersistenceService, never()).updateRaceWeekendStatus(any(), any());
    }

    @Test
    void shouldLeaveRaceWeekendUpcomingWhenMoreThanFourDaysAway() {
        givenToday(LocalDate.of(2025, 10, 5));
        RaceWeekend raceWeekend = aRaceWeekend(START_DATE, END_DATE, UPCOMING);
        when(raceWeekendPersistenceService.getIncompleteRaceWeekends()).thenReturn(List.of(raceWeekend));

        underTest.updateRaceWeekendStatus();

        verify(raceWeekendPersistenceService, never()).updateRaceWeekendStatus(any(), any());
    }

    @Test
    void shouldUpdateToRaceWeekOnTheMondayBeforeTheRaceWeekend() {
        givenToday(LocalDate.of(2025, 10, 6));
        RaceWeekend raceWeekend = aRaceWeekend(START_DATE, END_DATE, UPCOMING);
        when(raceWeekendPersistenceService.getIncompleteRaceWeekends()).thenReturn(List.of(raceWeekend));

        underTest.updateRaceWeekendStatus();

        verify(raceWeekendPersistenceService).updateRaceWeekendStatus(raceWeekend.getRaceWeekendUid(), RACE_WEEK);
    }

    @Test
    void shouldUpdateToLiveOnTheStartOfTheRaceWeekend() {
        givenToday(START_DATE);
        RaceWeekend raceWeekend = aRaceWeekend(START_DATE, END_DATE, RACE_WEEK);
        when(raceWeekendPersistenceService.getIncompleteRaceWeekends()).thenReturn(List.of(raceWeekend));

        underTest.updateRaceWeekendStatus();

        verify(raceWeekendPersistenceService).updateRaceWeekendStatus(raceWeekend.getRaceWeekendUid(), LIVE);
    }

    @Test
    void shouldStayLiveOnTheLastDayOfTheRaceWeekend() {
        givenToday(END_DATE);
        RaceWeekend raceWeekend = aRaceWeekend(START_DATE, END_DATE, LIVE);
        when(raceWeekendPersistenceService.getIncompleteRaceWeekends()).thenReturn(List.of(raceWeekend));

        underTest.updateRaceWeekendStatus();

        verify(raceWeekendPersistenceService, never()).updateRaceWeekendStatus(any(), any());
    }

    @Test
    void shouldCompleteLiveRaceWeekendTheDayAfterItEnds() {
        givenToday(END_DATE.plusDays(1));
        RaceWeekend raceWeekend = aRaceWeekend(START_DATE, END_DATE, LIVE);
        when(raceWeekendPersistenceService.getIncompleteRaceWeekends()).thenReturn(List.of(raceWeekend));

        underTest.updateRaceWeekendStatus();

        verify(raceWeekendPersistenceService).updateRaceWeekendStatus(raceWeekend.getRaceWeekendUid(), COMPLETE);
    }

    @Test
    void shouldCompleteUpcomingRaceWeekendThatIsAlreadyInThePast() {
        givenToday(LocalDate.of(2025, 9, 28));
        RaceWeekend raceWeekend = aRaceWeekend(LocalDate.of(2025, 9, 4), LocalDate.of(2025, 9, 5), UPCOMING);
        when(raceWeekendPersistenceService.getIncompleteRaceWeekends()).thenReturn(List.of(raceWeekend));

        underTest.updateRaceWeekendStatus();

        verify(raceWeekendPersistenceService).updateRaceWeekendStatus(raceWeekend.getRaceWeekendUid(), COMPLETE);
        verify(raceWeekendPersistenceService, never()).updateRaceWeekendStatus(raceWeekend.getRaceWeekendUid(), RACE_WEEK);
    }

    @Test
    void shouldCompleteStuckRaceWeekAndPromoteNextRaceWeekendBeforeIt() {
        givenToday(LocalDate.of(2025, 10, 7));
        RaceWeekend stuck = aRaceWeekend(LocalDate.of(2025, 9, 19), LocalDate.of(2025, 9, 21), RACE_WEEK);
        RaceWeekend next = aRaceWeekend(START_DATE, END_DATE, UPCOMING);
        when(raceWeekendPersistenceService.getIncompleteRaceWeekends()).thenReturn(List.of(stuck, next));

        underTest.updateRaceWeekendStatus();

        InOrder inOrder = inOrder(raceWeekendPersistenceService);
        inOrder.verify(raceWeekendPersistenceService).updateRaceWeekendStatus(stuck.getRaceWeekendUid(), COMPLETE);
        inOrder.verify(raceWeekendPersistenceService).updateRaceWeekendStatus(next.getRaceWeekendUid(), RACE_WEEK);
    }

    @Test
    void shouldOnlyAllowOneActiveRaceWeekendWhenDatesOverlap() {
        givenToday(START_DATE);
        RaceWeekend first = aRaceWeekend(START_DATE, END_DATE, UPCOMING);
        RaceWeekend overlapping = aRaceWeekend(START_DATE, END_DATE, UPCOMING);
        when(raceWeekendPersistenceService.getIncompleteRaceWeekends()).thenReturn(List.of(first, overlapping));

        underTest.updateRaceWeekendStatus();

        verify(raceWeekendPersistenceService).updateRaceWeekendStatus(first.getRaceWeekendUid(), LIVE);
        verify(raceWeekendPersistenceService, never()).updateRaceWeekendStatus(eq(overlapping.getRaceWeekendUid()), any());
    }

    @Test
    void shouldDemoteBeforePromotingSoUniqueActiveIndexesAreNotViolated() {
        givenToday(LocalDate.of(2025, 10, 7));
        // An earlier weekend added after a later one was already marked RACE_WEEK
        RaceWeekend earlier = aRaceWeekend(LocalDate.of(2025, 10, 8), LocalDate.of(2025, 10, 9), UPCOMING);
        RaceWeekend later = aRaceWeekend(START_DATE, END_DATE, RACE_WEEK);
        when(raceWeekendPersistenceService.getIncompleteRaceWeekends()).thenReturn(List.of(earlier, later));

        underTest.updateRaceWeekendStatus();

        InOrder inOrder = inOrder(raceWeekendPersistenceService);
        inOrder.verify(raceWeekendPersistenceService).updateRaceWeekendStatus(later.getRaceWeekendUid(), UPCOMING);
        inOrder.verify(raceWeekendPersistenceService).updateRaceWeekendStatus(earlier.getRaceWeekendUid(), RACE_WEEK);
    }

    private void givenToday(LocalDate today) {
        // Midday UTC is the same calendar day in London all year round
        when(instantSource.instant()).thenReturn(today.atTime(12, 0).toInstant(java.time.ZoneOffset.UTC));
    }

    private RaceWeekend aRaceWeekend(LocalDate raceWeekendStartDate, LocalDate raceWeekendEndDate, RaceWeekendState state) {
        return new RaceWeekend(
                UUID.randomUUID(),
                1,
                RaceName.AUSTRALIA,
                CountryCode.AU,
                Collections.emptyList(),
                null,
                null,
                null,
                raceWeekendStartDate,
                raceWeekendEndDate,
                new RaceWeekendStatus(state, Instant.EPOCH)
        );
    }
}
