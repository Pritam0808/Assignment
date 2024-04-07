package com.schedule.meeting.CalendarAssistant;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingConflictChecker {
    public boolean hasConflict(List<Meeting> calendar, LocalDateTime proposedStartTime, LocalDateTime proposedEndTime) {
        for (Meeting meeting : calendar) {
            LocalDateTime existingStartTime = meeting.getStartTime();
            LocalDateTime existingEndTime = meeting.getEndTime();
            if (proposedStartTime.isBefore(existingEndTime) && proposedEndTime.isAfter(existingStartTime)) {
                return true; // Conflict found
            }
        }
        return false; // No conflict
    }
}
