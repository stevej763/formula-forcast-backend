package com.steve.formulaforecast.service.prediction;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PredictionTypeService {

    private final PredictionTypePersistenceService predictionTypePersistenceService;

    public PredictionTypeService(PredictionTypePersistenceService predictionTypePersistenceService) {
        this.predictionTypePersistenceService = predictionTypePersistenceService;
    }

    public List<PredictionTypeDetail> getAllPredictionTypes() {
       return predictionTypePersistenceService.getAllPredictionTypes();
    }

    public Optional<PredictionTypeDetail> getPredictionTypeByUid(UUID predictionTypeUid) {
        return predictionTypePersistenceService.getPredictionTypeByUid(predictionTypeUid);
    }
}
