package com.steve.formulaforecast.api.validation;

import com.steve.formulaforecast.api.exception.RequestValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<RequestValidationResponse> handleRequestValidationException(RequestValidationException exception) {
        RequestValidationResponse requestValidationResponse = new RequestValidationResponse(exception.getMessage());
        return ResponseEntity.badRequest().body(requestValidationResponse);
    }
}
