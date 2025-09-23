package com.steve.formulaforecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Instant;
import java.time.InstantSource;

@SpringBootApplication
public class FormulaforecastApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormulaforecastApplication.class, args);
	}

    @Bean
    public InstantSource instantSource() {
        return InstantSource.system();
    }
}
