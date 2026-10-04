package com.steve.formulaforecast.api.result.model;

import java.util.List;
import java.util.UUID;

public record PredictionResultsResponse(UUID raceWeekendUid, List<PredictionResultDto> results) {
}
