package com.steve.formulaforecast.job;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JobDescription {
    String value();
}
