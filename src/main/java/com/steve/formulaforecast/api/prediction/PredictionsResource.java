package com.steve.formulaforecast.api.prediction;

import com.steve.formulaforecast.api.exception.RequestValidationException;
import com.steve.formulaforecast.api.prediction.model.prediction.PredictionRequest;
import com.steve.formulaforecast.api.prediction.model.prediction.PredictionResponse;
import com.steve.formulaforecast.service.Account.model.Account;
import com.steve.formulaforecast.service.authentication.AuthenticatedAccountProvider;
import com.steve.formulaforecast.service.prediction.DriverPrediction;
import com.steve.formulaforecast.service.prediction.PredictionService;
import com.steve.formulaforecast.service.team.UserTeamService;
import com.steve.formulaforecast.service.team.model.UserTeam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/predictions", produces = APPLICATION_JSON_VALUE)
public class PredictionsResource {

    private final PredictionService predictionService;
    private final AuthenticatedAccountProvider authenticatedAccountProvider;
    private final UserTeamService userTeamService;

    PredictionsResource(PredictionService predictionService, AuthenticatedAccountProvider authenticatedAccountProvider, UserTeamService userTeamService) {
        this.predictionService = predictionService;
        this.authenticatedAccountProvider = authenticatedAccountProvider;
        this.userTeamService = userTeamService;
    }

    @PostMapping("/make-prediction/qualifying-top-three")
    public ResponseEntity<PredictionResponse> postQualifyingTopThreePrediction() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/make-prediction/race-top-three")
    public ResponseEntity<PredictionResponse> postRaceTopThreePrediction() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/make-prediction")
    public ResponseEntity<PredictionResponse> postPrediction(@RequestBody PredictionRequest predictionRequest) {
        Account account = authenticatedAccountProvider.getAuthenticatedAccount();
        validateCurrentUserTeam(predictionRequest.userTeamUid(), account);
        DriverPrediction driverPrediction = mapToModel(predictionRequest);
        predictionService.makeDriverPrediction(driverPrediction);
        return ResponseEntity.ok(new PredictionResponse());
    }

    private void validateCurrentUserTeam(UUID userTeamUid, Account account) {
        userTeamService.getCurrentTeamForAccount(account.getAccountUid()).ifPresentOrElse(
                team -> validateUserOwnsTeam(userTeamUid, team),
                this::throwUserHasNoTeamException);
    }

    private void throwUserHasNoTeamException() {
        throw new RequestValidationException("User does not have a team to make predictions for");
    }

    private void validateUserOwnsTeam(UUID userTeamUid, UserTeam team) {
        if (!team.getTeamUid().equals(userTeamUid)) {
            throw new RequestValidationException("User does not own the team they are trying to make a prediction for");
        }
    }

    private DriverPrediction mapToModel(PredictionRequest predictionRequest) {
        return new DriverPrediction(
                predictionRequest.predictionTypeUid(),
                predictionRequest.userTeamUid(),
                predictionRequest.raceWeekendUid(),
                predictionRequest.driverUid());
    }

    @PostMapping("/make-prediction/driver-of-the-day")
    public ResponseEntity<PredictionResponse> postDriverOfTheDayPrediction() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/make-prediction/biggest-winner")
    public ResponseEntity<PredictionResponse> postBiggestWinnerPrediction() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/make-prediction/biggest-loser")
    public ResponseEntity<PredictionResponse> postBiggestLoserPrediction() {
        return ResponseEntity.ok().build();
    }
}
