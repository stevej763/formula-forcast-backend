package com.steve.formulaforecast.service.prediction;

import com.neovisionaries.i18n.CountryCode;
import com.steve.formulaforecast.api.prediction.model.prediction.RankedDriverPrediction;
import com.steve.formulaforecast.service.raceweekends.RaceWeekendPersistenceService;
import com.steve.formulaforecast.service.raceweekends.model.Qualifying;
import com.steve.formulaforecast.service.raceweekends.model.Race;
import com.steve.formulaforecast.service.raceweekends.model.RaceName;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekend;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.steve.formulaforecast.service.prediction.PredictionException.INVALID_PICKS;
import static com.steve.formulaforecast.service.prediction.PredictionException.PREDICTIONS_LOCKED;
import static com.steve.formulaforecast.service.prediction.PredictionException.RACE_WEEKEND_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = PredictionService.class)
class PredictionServiceTest {

    private static final UUID RACE_WEEKEND_UID = UUID.randomUUID();
    private static final UUID PREDICTION_TYPE_UID = UUID.randomUUID();
    private static final UUID USER_TEAM_UID = UUID.randomUUID();
    private static final Instant QUALIFYING_STARTS_AT = Instant.parse("2025-10-11T14:00:00Z");

    @Autowired
    private PredictionService underTest;

    @MockitoBean
    private PredictionPersistenceService predictionPersistenceService;

    @MockitoBean
    private PredictionTypeService predictionTypeService;

    @MockitoBean
    private RaceWeekendPersistenceService raceWeekendPersistenceService;

    @MockitoBean
    private InstantSource instantSource;

    @BeforeEach
    void setUp() {
        when(predictionTypeService.getPredictionTypeByUid(PREDICTION_TYPE_UID)).thenReturn(Optional.of(
                new PredictionTypeDetail(PREDICTION_TYPE_UID, PredictionType.FASTEST_LAP, "", PredictionSelectionType.DRIVER, 1, Instant.EPOCH)));
    }

    @Test
    void shouldAcceptPredictionBeforeQualifyingStarts() {
        givenRaceWeekend(RaceWeekendState.LIVE);
        givenNow(QUALIFYING_STARTS_AT.minusSeconds(1));

        underTest.makeDriverPrediction(aSingleDriverPrediction());

        verify(predictionPersistenceService).saveDriverPrediction(any());
    }

    @Test
    void shouldRejectPredictionOnceQualifyingStarts() {
        givenRaceWeekend(RaceWeekendState.LIVE);
        givenNow(QUALIFYING_STARTS_AT);

        PredictionException exception = assertThrows(PredictionException.class, () -> underTest.makeDriverPrediction(aSingleDriverPrediction()));

        assertEquals(PREDICTIONS_LOCKED, exception.getMessage());
        verifyNoInteractions(predictionPersistenceService);
    }

    @Test
    void shouldRejectPredictionForUnknownRaceWeekend() {
        when(raceWeekendPersistenceService.getRaceWeekend(RACE_WEEKEND_UID)).thenReturn(Optional.empty());

        PredictionException exception = assertThrows(PredictionException.class, () -> underTest.makeDriverPrediction(aSingleDriverPrediction()));

        assertEquals(RACE_WEEKEND_NOT_FOUND, exception.getMessage());
        verifyNoInteractions(predictionPersistenceService);
    }

    @Test
    void shouldRejectTheWrongNumberOfPicksForThePredictionType() {
        givenRaceWeekend(RaceWeekendState.RACE_WEEK);
        givenNow(QUALIFYING_STARTS_AT.minusSeconds(60));
        DriverPrediction twoPicks = new DriverPrediction(PREDICTION_TYPE_UID, USER_TEAM_UID, RACE_WEEKEND_UID,
                List.of(new RankedDriverPrediction(UUID.randomUUID(), 1), new RankedDriverPrediction(UUID.randomUUID(), 2)));

        PredictionException exception = assertThrows(PredictionException.class, () -> underTest.makeDriverPrediction(twoPicks));

        assertEquals(INVALID_PICKS, exception.getMessage());
        verifyNoInteractions(predictionPersistenceService);
    }

    @Test
    void shouldNeedOneDistinctDriverInEachPosition() {
        UUID driver = UUID.randomUUID();

        assertTrue(PredictionService.isOnePickPerPosition(List.of(
                new RankedDriverPrediction(UUID.randomUUID(), 2),
                new RankedDriverPrediction(UUID.randomUUID(), 1),
                new RankedDriverPrediction(UUID.randomUUID(), 3)), 3));
        assertFalse(PredictionService.isOnePickPerPosition(List.of(
                new RankedDriverPrediction(driver, 1),
                new RankedDriverPrediction(driver, 2),
                new RankedDriverPrediction(UUID.randomUUID(), 3)), 3), "same driver twice");
        assertFalse(PredictionService.isOnePickPerPosition(List.of(
                new RankedDriverPrediction(UUID.randomUUID(), 1),
                new RankedDriverPrediction(UUID.randomUUID(), 1),
                new RankedDriverPrediction(UUID.randomUUID(), 3)), 3), "position 2 missing");
        assertFalse(PredictionService.isOnePickPerPosition(List.of(new RankedDriverPrediction(UUID.randomUUID(), 2)), 1), "single pick not at rank 1");
        assertFalse(PredictionService.isOnePickPerPosition(List.of(), 1), "no picks");
    }

    private void givenNow(Instant now) {
        when(instantSource.instant()).thenReturn(now);
    }

    private void givenRaceWeekend(RaceWeekendState state) {
        RaceWeekend raceWeekend = new RaceWeekend(
                RACE_WEEKEND_UID,
                1,
                RaceName.AUSTRALIA,
                CountryCode.AU,
                Collections.emptyList(),
                new Qualifying(UUID.randomUUID(), QUALIFYING_STARTS_AT),
                null,
                new Race(UUID.randomUUID(), QUALIFYING_STARTS_AT.plusSeconds(86_400)),
                LocalDate.of(2025, 10, 10),
                LocalDate.of(2025, 10, 12),
                new RaceWeekendStatus(state, Instant.EPOCH));
        when(raceWeekendPersistenceService.getRaceWeekend(RACE_WEEKEND_UID)).thenReturn(Optional.of(raceWeekend));
    }

    private DriverPrediction aSingleDriverPrediction() {
        return new DriverPrediction(PREDICTION_TYPE_UID, USER_TEAM_UID, RACE_WEEKEND_UID,
                List.of(new RankedDriverPrediction(UUID.randomUUID(), 1)));
    }
}
