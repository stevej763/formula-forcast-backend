package com.steve.formulaforecast.service.prediction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionService.class);
    private final PredictionPersistenceService predictionPersistenceService;

    public PredictionService(PredictionPersistenceService predictionPersistenceService) {
        this.predictionPersistenceService = predictionPersistenceService;
    }

    public void makeDriverPrediction(DriverPrediction driverPrediction) {
        LOGGER.info("Making driver prediction for user team=[{}] for raceWeekend=[{}]",
                driverPrediction.getUserTeamUid(), driverPrediction.getRaceWeekendUid());
        predictionPersistenceService.saveDriverPrediction(driverPrediction);
    }
}
