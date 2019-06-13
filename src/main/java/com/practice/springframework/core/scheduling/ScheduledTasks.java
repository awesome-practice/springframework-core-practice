package com.practice.springframework.core.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * https://www.baeldung.com/spring-scheduled-tasks
 *
 * @author Luo Bao Ding
 * @since 2018/12/10
 */
@Component
public class ScheduledTasks {

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void scheduleFixedRateWithInitialDelayTask() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "Fixed rate task with one second initial delay - " + now);
    }

    @Scheduled(cron = "* * * ? * *")
    public void scheduleTaskUsingCronExpression() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);
        System.out.println("Current Thread : "+Thread.currentThread().getName());
    }


    @Scheduled(fixedDelayString = "${my.scheduling.fixedDelay.in.milliseconds}")
    public void fixedDelayString(){
        System.out.println("fixedDelayString: "+Instant.now());
    }

    @Scheduled(fixedRateString = "${my.scheduling.fixedRate.in.milliseconds}")
    public void fixedRateString (){
        System.out.println("fixedRateString: "+Instant.now());
    }

    @Scheduled(cron  = "${my.scheduling.cron}")
    public void cron  (){
        System.out.println("cron: "+Instant.now());
    }



}
