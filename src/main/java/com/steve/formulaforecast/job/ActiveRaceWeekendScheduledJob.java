package com.steve.formulaforecast.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@JobIdentifier("ActiveRaceWeekendScheduledJob")
@JobDescription("Job scheduled to run every minute to check for the current active race weekend")
public class ActiveRaceWeekendScheduledJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveRaceWeekendScheduledJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LOGGER.info("Testing scheduled job execution");
    }
}
