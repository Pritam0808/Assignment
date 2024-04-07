package com.schedule.meeting.CalendarAssistant;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalendarAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalendarAssistantApplication.class, args);

        // After the Spring Boot application starts, create and run the MeetingCLI instance
        MeetingCLI cli = new MeetingCLI();
        cli.run();
    }
}
