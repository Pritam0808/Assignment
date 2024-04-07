package com.schedule.meeting.CalendarAssistant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeetingSchedulerConfig {

    @Bean
    public MeetingScheduler meetingScheduler() {
        return new MeetingScheduler();
    }
}
