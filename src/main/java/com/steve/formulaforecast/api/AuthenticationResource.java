package com.steve.formulaforecast.api;

import com.steve.formulaforecast.api.model.account.AccountDetailsResponseDto;
import com.steve.formulaforecast.api.model.account.creation.AccountCreationRequestDto;
import com.steve.formulaforecast.api.model.account.creation.AccountCreationResponseDto;
import com.steve.formulaforecast.api.model.account.login.AccountLoginRequestDto;
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
    private final AuthCookieProperties authCookieConfig;

    AuthenticationResource(JwtService jwtService, AuthenticationService authenticationService, PasswordEncoder passwordEncoder, AuthCookieProperties authCookieConfig) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
        this.authCookieConfig = authCookieConfig;
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
        LOGGER.info("account created accountUid=[{}] email=[{}]", registeredUser.getAccountUid(), registeredUser.getEmail());
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
        Cookie emptyCookie = emptyCookie();
        response.addCookie(emptyCookie);
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
        emptyCookie.setDomain(authCookieConfig.getDomain());
        emptyCookie.setHttpOnly(authCookieConfig.httpOnly());
        emptyCookie.setSecure(authCookieConfig.isSecure());
        emptyCookie.setPath("/");
        emptyCookie.setAttribute("SameSite", authCookieConfig.sameSite());
        emptyCookie.setMaxAge(0);
        return emptyCookie;
    }

    private Cookie createAccessTokenCookie(String accessToken) {
        Cookie identityCookie = new Cookie("AccessToken", accessToken);
        identityCookie.setDomain(authCookieConfig.getDomain());
        identityCookie.setHttpOnly(authCookieConfig.httpOnly());
        identityCookie.setSecure(authCookieConfig.isSecure());
        identityCookie.setPath("/");
        identityCookie.setAttribute("SameSite", authCookieConfig.sameSite());
        identityCookie.setMaxAge((int) (jwtService.getExpirationTime() / 1000));
        return identityCookie;
    }

    @GetMapping("/current-user")
    public ResponseEntity<AccountDetailsResponseDto> getCurrentAuthenticatedUser(Authentication authentication) {
        if (isNull(authentication)) {
            LOGGER.info("No existing authentication");
            return ResponseEntity.ok(AccountDetailsResponseDto.unauthenticated());
        }
        AccountAuthenticationUserDetailsEntity principal = (AccountAuthenticationUserDetailsEntity) authentication.getPrincipal();
        if (nonNull(principal)) {
            LOGGER.info("Got info from authenticated user=[{}]", principal.accountUid());
            return ResponseEntity.ok(new AccountDetailsResponseDto(principal.accountUid(), principal.firstName(), principal.lastName(), principal.email(), principal.phoneNumber(), true));
        }
        return ResponseEntity.ok(AccountDetailsResponseDto.unauthenticated());

    }
}