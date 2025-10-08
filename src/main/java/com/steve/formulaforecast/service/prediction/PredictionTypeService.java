package com.steve.formulaforecast.service.prediction;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictionTypeService {

    private final PredictionTypePersistenceService predictionTypePersistenceService;

    public PredictionTypeService(PredictionTypePersistenceService predictionTypePersistenceService) {
        this.predictionTypePersistenceService = predictionTypePersistenceService;
    }

    public List<PredictionTypeDetail> getAllPredictionTypes() {
       return predictionTypePersistenceService.getAllPredictionTypes();
    }
}
