package com.steve.formulaforecast.api.model.prediction;

import java.util.List;

public record PredictionTypesResponse(List<PreditionTypeDetailDto> predictionTypes) {
}
