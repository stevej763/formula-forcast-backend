package com.steve.formulaforecast;

import com.steve.formulaforecast.api.exception.RequestValidationException;
import com.steve.formulaforecast.persistence.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.steve.formulaforecast.api.exception.RequestValidationException.*;

@Configuration
public class ApplicationAuthenticationConfiguration {


    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAuthenticationConfiguration.class);
    private final AccountRepository accountRepository;

    public ApplicationAuthenticationConfiguration(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> accountRepository.selectUserDetailsByEmail(username)
                .orElseThrow(() -> new RequestValidationException(USER_NOT_FOUND));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}