package com.steve.formulaforecast.service.prediction;

import com.steve.formulaforecast.persistence.entity.prediction.PredictionTypeEntity;
import com.steve.formulaforecast.persistence.PredictionTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PredictionTypePersistenceService {

    private final PredictionTypeRepository predictionTypeRepository;

    public PredictionTypePersistenceService(PredictionTypeRepository predictionTypeRepository) {
        this.predictionTypeRepository = predictionTypeRepository;
    }

    @Transactional
    public List<PredictionTypeDetail> getAllPredictionTypes() {
        return predictionTypeRepository.selectAllEnabledPredictionTypes().map(this::toModel).toList();
    }

    private PredictionTypeDetail toModel(PredictionTypeEntity predictionTypeEntity) {
        return new PredictionTypeDetail(
                predictionTypeEntity.predictionTypeUid(),
                predictionTypeEntity.predictionType(),
                predictionTypeEntity.description(),
                predictionTypeEntity.predictionSelectionType(),
                predictionTypeEntity.createdAt());
    }

    public Optional<PredictionTypeDetail> getPredictionTypeByUid(UUID predictionTypeUid) {
        return predictionTypeRepository.selectPredictionTypeByUid(predictionTypeUid).map(this::toModel);
    }
}
