package com.steve.formulaforecast.api.result;

import com.steve.formulaforecast.api.exception.RequestValidationException;
import com.steve.formulaforecast.api.prediction.model.prediction.RankedDriverPrediction;
import com.steve.formulaforecast.api.result.model.PredictionResultDto;
import com.steve.formulaforecast.api.result.model.PredictionResultRequest;
import com.steve.formulaforecast.api.result.model.PredictionResultsResponse;
import com.steve.formulaforecast.api.result.model.ResultDriverDto;
import com.steve.formulaforecast.api.result.model.ResultSubmissionResponse;
import com.steve.formulaforecast.service.result.PredictionResult;
import com.steve.formulaforecast.service.result.PredictionResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/race-weekend/{raceWeekendUid}/results", produces = APPLICATION_JSON_VALUE)
public class PredictionResultResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionResultResource.class);
    private static final String INVALID_RESULT = "INVALID_RESULT";

    private final PredictionResultService predictionResultService;

    PredictionResultResource(PredictionResultService predictionResultService) {
        this.predictionResultService = predictionResultService;
    }

    @GetMapping
    public ResponseEntity<PredictionResultsResponse> getResults(@PathVariable UUID raceWeekendUid) {
        List<PredictionResultDto> results = predictionResultService.getResults(raceWeekendUid).stream().map(this::toDto).toList();
        return ResponseEntity.ok(new PredictionResultsResponse(raceWeekendUid, results));
    }

    /**
     * Enters or corrects the result for one prediction type and scores everyone's predictions of that type.
     */
    @PutMapping("/{predictionTypeUid}")
    public ResponseEntity<ResultSubmissionResponse> submitResult(
            @PathVariable UUID raceWeekendUid,
            @PathVariable UUID predictionTypeUid,
            @RequestBody PredictionResultRequest predictionResultRequest) {
        if (predictionResultRequest == null || predictionResultRequest.drivers() == null) {
            throw new RequestValidationException(INVALID_RESULT);
        }
        LOGGER.info("Submitting result for raceWeekend=[{}] predictionType=[{}]", raceWeekendUid, predictionTypeUid);
        List<RankedDriverPrediction> drivers = predictionResultRequest.drivers().stream()
                .map(driver -> new RankedDriverPrediction(driver.driverUid(), driver.rank()))
                .toList();
        int predictionsScored = predictionResultService.submitResult(new PredictionResult(raceWeekendUid, predictionTypeUid, drivers));
        return ResponseEntity.ok(new ResultSubmissionResponse(predictionsScored));
    }

    private PredictionResultDto toDto(PredictionResult predictionResult) {
        return new PredictionResultDto(
                predictionResult.predictionTypeUid(),
                predictionResult.drivers().stream().map(driver -> new ResultDriverDto(driver.getDriverUid(), driver.getRank())).toList());
    }
}
