package com.steve.formulaforecast.service.authentication;

import com.steve.formulaforecast.service.Account.AccountPersistenceService;
import com.steve.formulaforecast.service.Account.model.Account;
import com.steve.formulaforecast.service.authentication.model.AccountCreationRequest;
import com.steve.formulaforecast.service.authentication.model.AccountLoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    private final AccountPersistenceService accountPersistenceService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            AccountPersistenceService accountPersistenceService,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.accountPersistenceService = accountPersistenceService;
    }

    @Transactional
    public Account createAccount(AccountCreationRequest accountCreationRequest) {
        accountPersistenceService.createAccount(accountCreationRequest);
        UUID accountUid = accountCreationRequest.getAccountUid();
        return accountPersistenceService.getAccountByUid(accountUid).orElseThrow();
    }

    public Account authenticateAccount(AccountLoginRequest accountLoginRequest) {
        UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(
                accountLoginRequest.getEmail(), accountLoginRequest.getPassword());
        LOGGER.info("Authenticating request=[{}]", request);
        try {
            Authentication authResult = authenticationManager.authenticate(request);
            LOGGER.info("Authenticated: {}", authResult.isAuthenticated());
            return accountPersistenceService.getAccountByEmail(accountLoginRequest.getEmail())
                    .orElseThrow();
        } catch (Exception e) {
            LOGGER.error("Exception when authenticating:", e);
        }
       throw new RuntimeException();
    }
}