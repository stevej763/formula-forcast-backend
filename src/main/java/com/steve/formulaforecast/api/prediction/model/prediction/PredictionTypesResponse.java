package com.steve.formulaforecast.api.prediction.model.prediction;

import java.util.List;

public record PredictionTypesResponse(List<PreditionTypeDetailDto> predictionTypes) {
}
