package com.steve.formulaforecast.api.validation;

import com.steve.formulaforecast.api.exception.RequestValidationException;
import com.steve.formulaforecast.service.leaderboard.ChampionshipSeasonException;
import com.steve.formulaforecast.service.prediction.PredictionException;
import com.steve.formulaforecast.service.raceweekends.RaceWeekendCreationException;
import com.steve.formulaforecast.service.result.ResultException;
import com.steve.formulaforecast.service.team.TeamCreationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            RequestValidationException.class,
            TeamCreationException.class,
            ChampionshipSeasonException.class,
            PredictionException.class,
            RaceWeekendCreationException.class,
            ResultException.class
    })
    public ResponseEntity<RequestValidationResponse> handleRequestValidationException(RuntimeException exception) {
        RequestValidationResponse requestValidationResponse = new RequestValidationResponse(exception.getMessage());
        return ResponseEntity.badRequest().body(requestValidationResponse);
    }
}
