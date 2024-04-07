package com.schedule.meeting.CalendarAssistant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeetingConflictCheckerConfig {

 @Bean
 public MeetingConflictChecker meetingConflictChecker() {
     return new MeetingConflictChecker();
 }
}