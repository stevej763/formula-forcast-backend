package com.steve.formulaforecast.service.result;

import com.steve.formulaforecast.api.prediction.model.prediction.RankedDriverPrediction;
import com.steve.formulaforecast.persistence.PredictionResultRepository;
import com.steve.formulaforecast.persistence.entity.result.PredictionChoiceForScoringEntity;
import com.steve.formulaforecast.persistence.entity.result.ResultChoiceEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PredictionResultPersistenceService {

    private final PredictionResultRepository predictionResultRepository;

    public PredictionResultPersistenceService(PredictionResultRepository predictionResultRepository) {
        this.predictionResultRepository = predictionResultRepository;
    }

    /**
     * Saves a result, replacing any result already entered for the same weekend and prediction type.
     */
    @Transactional
    public void saveResult(PredictionResult predictionResult, Instant now) {
        predictionResultRepository.upsertResult(UUID.randomUUID(), predictionResult.raceWeekendUid(), predictionResult.predictionTypeUid(), now);
        predictionResultRepository.deleteResultChoices(predictionResult.raceWeekendUid(), predictionResult.predictionTypeUid());
        predictionResult.drivers().forEach(driver -> predictionResultRepository.insertResultChoice(
                predictionResult.raceWeekendUid(),
                predictionResult.predictionTypeUid(),
                driver.getDriverUid(),
                driver.getRank()));
    }

    @Transactional
    public List<PredictionResult> getResults(UUID raceWeekendUid) {
        Map<UUID, List<RankedDriverPrediction>> driversByType = predictionResultRepository.selectResultChoices(raceWeekendUid)
                .collect(Collectors.groupingBy(
                        ResultChoiceEntity::predictionTypeUid,
                        LinkedHashMap::new,
                        Collectors.mapping(choice -> new RankedDriverPrediction(choice.driverUid(), choice.rank()), Collectors.toList())));
        return driversByType.entrySet().stream()
                .map(entry -> new PredictionResult(raceWeekendUid, entry.getKey(), entry.getValue()))
                .toList();
    }

    /**
     * The picks of every prediction of a type for a weekend, keyed by prediction uid.
     */
    @Transactional
    public Map<UUID, List<RankedDriverPrediction>> getPredictionPicks(UUID raceWeekendUid, UUID predictionTypeUid) {
        return predictionResultRepository.selectPredictionChoicesForScoring(raceWeekendUid, predictionTypeUid)
                .collect(Collectors.groupingBy(
                        PredictionChoiceForScoringEntity::predictionUid,
                        Collectors.mapping(choice -> new RankedDriverPrediction(choice.driverUid(), choice.rank()), Collectors.toList())));
    }

    @Transactional
    public void savePoints(UUID predictionUid, int points, Instant scoredAt) {
        predictionResultRepository.updatePredictionPoints(predictionUid, points, scoredAt);
    }
}
