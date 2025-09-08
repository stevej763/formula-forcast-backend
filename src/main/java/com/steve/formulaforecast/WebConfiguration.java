package com.steve.formulaforecast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration {


    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfiguration.class);

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                LOGGER.info("Using CORS config");
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
