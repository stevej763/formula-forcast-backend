package com.steve.formulaforecast.service.prediction;

import com.steve.formulaforecast.api.model.prediction.FastestLapPredictionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {


    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionService.class);
    private final PredicationPersistenceService predicationPersistenceService;

    public PredictionService(PredicationPersistenceService predicationPersistenceService) {
        this.predicationPersistenceService = predicationPersistenceService;
    }

    public void makeFastestLapPrediction(FastestLapPrediction fastestLapPrediction) {
        LOGGER.info("Making fastest lap prediction for user team=[{}] for raceWeekend=[{}]",
                fastestLapPrediction.getUserTeamUid(), fastestLapPrediction.getRaceWeekendUid());
        predicationPersistenceService.saveFastestLapPrediction(fastestLapPrediction);
    }
}
