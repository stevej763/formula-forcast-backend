package com.steve.formulaforecast.job;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class JobConfiguration {

    @Bean
    public JobDetail activeRaceWeekendScheduledJobDetail() {
        return JobBuilder.newJob().ofType(ActiveRaceWeekendScheduledJob.class)
                .storeDurably()
                .withIdentity(ActiveRaceWeekendScheduledJob.class.getAnnotation(JobIdentifier.class).value())
                .withDescription(ActiveRaceWeekendScheduledJob.class.getAnnotation(JobDescription.class).value())
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail activeRaceWeekendScheduledJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(activeRaceWeekendScheduledJobDetail)
                .withIdentity("ActiveRaceWeekendScheduledJobIdentity")
                .withDescription(ActiveRaceWeekendScheduledJob.class.getAnnotation(JobIdentifier.class).value() + "Trigger")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(1))
                .build();
    }
}
