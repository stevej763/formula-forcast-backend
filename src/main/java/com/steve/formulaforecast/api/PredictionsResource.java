package com.steve.formulaforecast.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/predictions", produces = APPLICATION_JSON_VALUE)
public class PredictionsResource {

    @PostMapping("/make-prediction/qualifying-top-three")
    public ResponseEntity<PredictionResponse> postQualifyingTopThreePrediction() {
        return ResponseEntity.ok().build();
    }

}
