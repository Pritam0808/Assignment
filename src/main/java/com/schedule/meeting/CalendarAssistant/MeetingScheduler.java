package com.schedule.meeting.CalendarAssistant;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingScheduler {

    private static final int WORK_START_HOUR = 9;
    private static final int WORK_END_HOUR = 18;

    public List<List<LocalDateTime>> findFreeSlots(List<Meeting> employee1Calendar, List<Meeting> employee2Calendar, Duration meetingDuration) {
        List<Meeting> combinedCalendar = new ArrayList<>(employee1Calendar);
        combinedCalendar.addAll(employee2Calendar);
        combinedCalendar.sort((m1, m2) -> m1.getStartTime().compareTo(m2.getStartTime()));

        List<List<LocalDateTime>> freeSlots = new ArrayList<>();
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(WORK_START_HOUR, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(WORK_END_HOUR, 0));

        for (Meeting meeting : combinedCalendar) {
            LocalDateTime meetingStart = meeting.getStartTime();
            LocalDateTime meetingEnd = meeting.getEndTime();

            if (start.isBefore(meetingStart)) {
                List<LocalDateTime> slot = new ArrayList<>();
                slot.add(start);
                slot.add(meetingStart);
                freeSlots.add(slot);
            }

            if (end.isBefore(meetingEnd)) {
                end = meetingEnd;
            }
            start = meetingEnd;
        }

        if (start.isBefore(end)) {
            List<LocalDateTime> slot = new ArrayList<>();
            slot.add(start);
            slot.add(end);
            freeSlots.add(slot);
        }

        return freeSlots;
    }
}
