package com.steve.formulaforecast.api.result.model;

import java.util.List;
import java.util.UUID;

public record PredictionResultDto(UUID predictionTypeUid, List<ResultDriverDto> drivers) {
}
