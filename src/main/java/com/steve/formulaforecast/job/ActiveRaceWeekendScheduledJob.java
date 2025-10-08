package com.steve.formulaforecast.job;

import com.steve.formulaforecast.service.raceweekends.ActiveRaceWeekendUpdateService;
import com.steve.formulaforecast.job.config.JobDescription;
import com.steve.formulaforecast.job.config.JobIdentifier;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@JobIdentifier("ActiveRaceWeekendScheduledJob")
@JobDescription("Job scheduled to run every minute to check for the current active race weekend")
public class ActiveRaceWeekendScheduledJob implements Job {

    private final ActiveRaceWeekendUpdateService activeRaceWeekendUpdateService;

    ActiveRaceWeekendScheduledJob(
            ActiveRaceWeekendUpdateService activeRaceWeekendUpdateService){
        this.activeRaceWeekendUpdateService = activeRaceWeekendUpdateService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        activeRaceWeekendUpdateService.updateRaceWeekendStatus();
    }

}
