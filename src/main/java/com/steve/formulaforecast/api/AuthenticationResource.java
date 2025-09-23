package com.steve.formulaforecast.api;

import com.steve.formulaforecast.api.model.account.creation.AccountCreationRequestDto;
import com.steve.formulaforecast.api.model.account.creation.AccountCreationResponseDto;
import com.steve.formulaforecast.api.model.account.login.AccountLoginRequestDto;
import com.steve.formulaforecast.api.model.account.login.AccountLoginResponseDto;
import com.steve.formulaforecast.persistence.entity.account.AccountAuthenticationUserDetailsEntity;
import com.steve.formulaforecast.service.Account.model.Account;
import com.steve.formulaforecast.service.authentication.AuthenticationService;
import com.steve.formulaforecast.service.authentication.JwtService;
import com.steve.formulaforecast.service.authentication.model.AccountCreationRequest;
import com.steve.formulaforecast.service.authentication.model.AccountLoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/auth", produces = APPLICATION_JSON_VALUE)
public class AuthenticationResource {


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationResource.class);
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    AuthenticationResource(JwtService jwtService, AuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create-account")
    public ResponseEntity<AccountCreationResponseDto> createAccount(@RequestBody AccountCreationRequestDto accountCreationRequestDto) {
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest(
                UUID.randomUUID(),
                accountCreationRequestDto.firstName(),
                accountCreationRequestDto.lastName(),
                accountCreationRequestDto.email(),
                accountCreationRequestDto.phoneNumber(),
                passwordEncoder.encode(accountCreationRequestDto.password()));
        LOGGER.info("Account creation request received");
        Account registeredUser = authenticationService.createAccount(accountCreationRequest);
        return ResponseEntity.ok(new AccountCreationResponseDto(registeredUser.getAccountUid()));
    }

    @PutMapping("/login")
    public void login(@RequestBody AccountLoginRequestDto accountLoginRequestDto, HttpServletResponse response) {
        LOGGER.info("Finding account");
        Account account = authenticationService.authenticateAccount(new AccountLoginRequest(accountLoginRequestDto.email(), accountLoginRequestDto.password()));
        LOGGER.info("Found authenticated account");
        String jwtToken = jwtService.generateToken(account);
        LOGGER.info("Generated JWT ");
        Cookie identityCookie = createAccessTokenCookie(jwtToken);
        response.addCookie(identityCookie);
    }

    @DeleteMapping("/logout")
    public void logout(HttpServletResponse response, Authentication authentication) {
        Cookie identityCookie = emptyCookie();
        response.addCookie(identityCookie);
        logUserLoggedOut(authentication);
    }

    private void logUserLoggedOut(Authentication authentication) {
        if (nonNull(authentication)) {
            AccountAuthenticationUserDetailsEntity principal = (AccountAuthenticationUserDetailsEntity) authentication.getPrincipal();
            if (Objects.nonNull(principal)) {
                LOGGER.info("Logging out user=[{}]", principal.accountUid());
            }
        }
    }

    private Cookie emptyCookie() {
        Cookie emptyCookie = new Cookie("AccessToken", "");
        emptyCookie.setHttpOnly(true);              // cannot be accessed by JS
        emptyCookie.setSecure(true);                // send only over HTTPS (disable in local dev if needed)
        emptyCookie.setPath("/");                   // cookie is valid for entire domain
        emptyCookie.setMaxAge(0); // expiration in seconds
        return emptyCookie;
    }

    private Cookie createAccessTokenCookie(String jwtToken) {
        Cookie identityCookie = new Cookie("AccessToken", jwtToken);
        identityCookie.setHttpOnly(true);              // cannot be accessed by JS
        identityCookie.setSecure(true);                // send only over HTTPS (disable in local dev if needed)
        identityCookie.setPath("/");                   // cookie is valid for entire domain
        identityCookie.setMaxAge((int) (jwtService.getExpirationTime() / 1000)); // expiration in seconds
        return identityCookie;
    }

    @GetMapping("/current-user")
    public ResponseEntity<AccountDetailsResponseDto> getCurrentAuthenticatedUser(Authentication authentication) {
        AccountAuthenticationUserDetailsEntity principal = (AccountAuthenticationUserDetailsEntity) authentication.getPrincipal();
        LOGGER.info("Got info from authenticated user=[{}]", principal);
        if (isNull(principal)) {
            throw new RuntimeException();
        }
        return ResponseEntity.ok(new AccountDetailsResponseDto(principal.accountUid(), principal.firstName(), principal.lastName(), principal.email(), principal.phoneNumber(), true));
    }
}