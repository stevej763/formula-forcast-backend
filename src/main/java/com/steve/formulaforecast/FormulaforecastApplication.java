package com.steve.formulaforecast;

import com.steve.formulaforecast.api.authentication.AuthCookieProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.InstantSource;

@SpringBootApplication
@EnableConfigurationProperties(AuthCookieProperties.class)
public class FormulaforecastApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormulaforecastApplication.class, args);
	}

    @Bean
    public InstantSource instantSource() {
        return InstantSource.system();
    }
}
