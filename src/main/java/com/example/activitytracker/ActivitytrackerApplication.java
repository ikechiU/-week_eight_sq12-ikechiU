package com.example.activitytracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.time.Clock;
import java.util.TimeZone;

@SpringBootApplication
public class ActivitytrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitytrackerApplication.class, args);
    }

    @PostConstruct
    void setUTCTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

}
