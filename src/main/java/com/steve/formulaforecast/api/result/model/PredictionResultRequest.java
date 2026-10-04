package com.steve.formulaforecast.api.result.model;

import java.util.List;

public record PredictionResultRequest(List<ResultDriverDto> drivers) {
}
