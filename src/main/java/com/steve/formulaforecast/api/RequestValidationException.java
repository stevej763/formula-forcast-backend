package com.steve.formulaforecast.api;

public class RequestValidationException extends RuntimeException {

    public static final String INVALID_LOGIN_ATTEMPT = "INVALID_LOGIN_ATTEMPT";
    public static final String ACCOUNT_WITH_EMAIL_ALREADY_EXISTS = "ACCOUNT_WITH_EMAIL_ALREADY_EXISTS";
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";

    public RequestValidationException(String message) {
        super(message);
    }
}
