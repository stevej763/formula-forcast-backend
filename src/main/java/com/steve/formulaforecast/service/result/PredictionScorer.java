package com.steve.formulaforecast.service.result;

import com.steve.formulaforecast.api.prediction.model.prediction.RankedDriverPrediction;

import java.util.List;

/**
 * Scores one point for each pick that matches the result: the same driver in the same position.
 * <p>
 * A single driver prediction scores 1 if its driver is in the result, including when the result is a tie.
 * A top three prediction scores 1 for each driver in exactly the right position, so up to 3.
 */
public final class PredictionScorer {

    private PredictionScorer() {
    }

    public static int score(List<RankedDriverPrediction> picks, List<RankedDriverPrediction> result) {
        return (int) picks.stream()
                .filter(pick -> result.stream().anyMatch(actual ->
                        actual.getDriverUid().equals(pick.getDriverUid()) && actual.getRank() == pick.getRank()))
                .count();
    }
}
