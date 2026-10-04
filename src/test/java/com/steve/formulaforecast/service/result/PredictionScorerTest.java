package com.steve.formulaforecast.service.result;

import com.steve.formulaforecast.api.prediction.model.prediction.RankedDriverPrediction;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PredictionScorerTest {

    private static final UUID NORRIS = UUID.randomUUID();
    private static final UUID PIASTRI = UUID.randomUUID();
    private static final UUID VERSTAPPEN = UUID.randomUUID();
    private static final UUID RUSSELL = UUID.randomUUID();

    @Test
    void shouldScoreOneForTheRightSingleDriver() {
        assertEquals(1, PredictionScorer.score(List.of(pick(NORRIS, 1)), List.of(pick(NORRIS, 1))));
        assertEquals(0, PredictionScorer.score(List.of(pick(PIASTRI, 1)), List.of(pick(NORRIS, 1))));
    }

    @Test
    void shouldScoreEitherDriverInATiedSingleDriverResult() {
        List<RankedDriverPrediction> tie = List.of(pick(NORRIS, 1), pick(RUSSELL, 1));

        assertEquals(1, PredictionScorer.score(List.of(pick(RUSSELL, 1)), tie));
        assertEquals(0, PredictionScorer.score(List.of(pick(PIASTRI, 1)), tie));
    }

    @Test
    void shouldScoreOneForEachTopThreeDriverInTheRightPosition() {
        List<RankedDriverPrediction> podium = List.of(pick(NORRIS, 1), pick(PIASTRI, 2), pick(VERSTAPPEN, 3));

        assertEquals(3, PredictionScorer.score(List.of(pick(NORRIS, 1), pick(PIASTRI, 2), pick(VERSTAPPEN, 3)), podium));
        assertEquals(1, PredictionScorer.score(List.of(pick(NORRIS, 1), pick(VERSTAPPEN, 2), pick(PIASTRI, 3)), podium));
        assertEquals(0, PredictionScorer.score(List.of(pick(PIASTRI, 1), pick(VERSTAPPEN, 2), pick(NORRIS, 3)), podium), "right drivers, all in the wrong places");
        assertEquals(0, PredictionScorer.score(List.of(pick(RUSSELL, 1), pick(RUSSELL, 2), pick(RUSSELL, 3)), podium));
    }

    @Test
    void shouldAcceptTiesOnlyInSingleDriverResults() {
        assertTrue(PredictionResultService.isValidResult(List.of(pick(NORRIS, 1), pick(RUSSELL, 1)), 1));
        assertFalse(PredictionResultService.isValidResult(List.of(pick(NORRIS, 2)), 1));
        assertTrue(PredictionResultService.isValidResult(List.of(pick(NORRIS, 1), pick(PIASTRI, 2), pick(VERSTAPPEN, 3)), 3));
        assertFalse(PredictionResultService.isValidResult(List.of(pick(NORRIS, 1), pick(PIASTRI, 1), pick(VERSTAPPEN, 3)), 3));
        assertFalse(PredictionResultService.isValidResult(List.of(pick(NORRIS, 1), pick(NORRIS, 2), pick(VERSTAPPEN, 3)), 3));
        assertFalse(PredictionResultService.isValidResult(List.of(), 1));
    }

    private static RankedDriverPrediction pick(UUID driverUid, int rank) {
        return new RankedDriverPrediction(driverUid, rank);
    }
}
