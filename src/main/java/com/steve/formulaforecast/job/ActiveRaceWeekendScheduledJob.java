package com.steve.formulaforecast.job;

import com.steve.formulaforecast.api.model.raceweekend.ActiveRaceWeekendUpdateService;
import com.steve.formulaforecast.job.config.JobDescription;
import com.steve.formulaforecast.job.config.JobIdentifier;
import com.steve.formulaforecast.service.raceweekends.RaceWeekendPersistenceService;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekend;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.Optional;

import static com.steve.formulaforecast.TimeZones.LONDON_TIME;

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
