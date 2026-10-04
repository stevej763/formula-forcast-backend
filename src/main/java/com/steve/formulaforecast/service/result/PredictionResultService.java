package com.steve.formulaforecast.service.result;

import com.steve.formulaforecast.api.prediction.model.prediction.RankedDriverPrediction;
import com.steve.formulaforecast.service.prediction.PredictionTypeDetail;
import com.steve.formulaforecast.service.prediction.PredictionTypeService;
import com.steve.formulaforecast.service.raceweekends.RaceWeekendPersistenceService;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.InstantSource;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.steve.formulaforecast.service.result.ResultException.INVALID_RESULT;
import static com.steve.formulaforecast.service.result.ResultException.PREDICTION_TYPE_NOT_FOUND;
import static com.steve.formulaforecast.service.result.ResultException.RACE_WEEKEND_NOT_FOUND;
import static com.steve.formulaforecast.service.result.ResultException.RESULTS_BEFORE_LOCK;

@Service
public class PredictionResultService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionResultService.class);

    private final PredictionResultPersistenceService predictionResultPersistenceService;
    private final PredictionTypeService predictionTypeService;
    private final RaceWeekendPersistenceService raceWeekendPersistenceService;
    private final InstantSource instantSource;

    public PredictionResultService(
            PredictionResultPersistenceService predictionResultPersistenceService,
            PredictionTypeService predictionTypeService,
            RaceWeekendPersistenceService raceWeekendPersistenceService,
            InstantSource instantSource) {
        this.predictionResultPersistenceService = predictionResultPersistenceService;
        this.predictionTypeService = predictionTypeService;
        this.raceWeekendPersistenceService = raceWeekendPersistenceService;
        this.instantSource = instantSource;
    }

    /**
     * Saves the result for one prediction type and scores every prediction of that type for the weekend.
     * Entering a corrected result re-scores from scratch.
     *
     * @return the number of predictions scored
     */
    @Transactional
    public int submitResult(PredictionResult predictionResult) {
        Instant now = instantSource.instant();
        RaceWeekend raceWeekend = raceWeekendPersistenceService.getRaceWeekend(predictionResult.raceWeekendUid())
                .orElseThrow(() -> new ResultException(RACE_WEEKEND_NOT_FOUND));
        if (now.isBefore(raceWeekend.getPredictionsLockAt())) {
            // Picks can still change until they lock, so scoring before then would go stale
            throw new ResultException(RESULTS_BEFORE_LOCK);
        }
        PredictionTypeDetail predictionType = predictionTypeService.getPredictionTypeByUid(predictionResult.predictionTypeUid())
                .orElseThrow(() -> new ResultException(PREDICTION_TYPE_NOT_FOUND));
        if (!isValidResult(predictionResult.drivers(), predictionType.getSelectionCount())) {
            throw new ResultException(INVALID_RESULT);
        }

        predictionResultPersistenceService.saveResult(predictionResult, now);

        Map<UUID, List<RankedDriverPrediction>> picksByPrediction =
                predictionResultPersistenceService.getPredictionPicks(predictionResult.raceWeekendUid(), predictionResult.predictionTypeUid());
        picksByPrediction.forEach((predictionUid, picks) ->
                predictionResultPersistenceService.savePoints(predictionUid, PredictionScorer.score(picks, predictionResult.drivers()), now));
        LOGGER.info("Saved [{}] result for raceWeekend=[{}] and scored [{}] predictions",
                predictionType.getPredictionType(), predictionResult.raceWeekendUid(), picksByPrediction.size());
        return picksByPrediction.size();
    }

    @Transactional
    public List<PredictionResult> getResults(UUID raceWeekendUid) {
        return predictionResultPersistenceService.getResults(raceWeekendUid);
    }

    /**
     * A single driver result needs one or more drivers, all at rank 1, so ties can be recorded.
     * A ranked result such as a top three needs exactly one driver in each position.
     */
    static boolean isValidResult(List<RankedDriverPrediction> drivers, int selectionCount) {
        if (drivers.isEmpty() || drivers.stream().anyMatch(driver -> driver.getDriverUid() == null)) {
            return false;
        }
        if (drivers.stream().map(RankedDriverPrediction::getDriverUid).distinct().count() != drivers.size()) {
            return false;
        }
        if (selectionCount == 1) {
            return drivers.stream().allMatch(driver -> driver.getRank() == 1);
        }
        List<Integer> ranks = drivers.stream().map(RankedDriverPrediction::getRank).sorted().toList();
        return ranks.equals(IntStream.rangeClosed(1, selectionCount).boxed().toList());
    }
}
