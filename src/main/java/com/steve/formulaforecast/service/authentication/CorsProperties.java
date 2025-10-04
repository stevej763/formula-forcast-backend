package com.steve.formulaforecast.service.authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "cors")
@Configuration
public class CorsProperties {

    private final List<String> allowedOrigins;

    public CorsProperties(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

}
