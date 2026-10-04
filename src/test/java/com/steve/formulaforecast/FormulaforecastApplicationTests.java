package com.steve.formulaforecast;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
public class FormulaforecastApplicationTests {
    @Test
	void contextLoads() {
	}

}
