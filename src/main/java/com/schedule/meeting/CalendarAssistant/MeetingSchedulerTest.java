package com.schedule.meeting.CalendarAssistant;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MeetingSchedulerTest {

    private MeetingScheduler meetingScheduler;
    private MeetingConflictChecker conflictChecker;

    @Before
    public void setUp() {
        meetingScheduler = new MeetingScheduler();
        conflictChecker = new MeetingConflictChecker();
    }

    @Test
    public void testNoConflictWithEmptyCalendars() {
        List<Meeting> employee1Calendar = new ArrayList<>();
        List<Meeting> employee2Calendar = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.of(2024, 4, 7, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 4, 7, 10, 0);

        assertFalse(conflictChecker.hasConflict(employee1Calendar, startTime, endTime));
        assertFalse(conflictChecker.hasConflict(employee2Calendar, startTime, endTime));
    }

    @Test
    public void testConflictWithOverlappingMeeting() {
        LocalDateTime startTime1 = LocalDateTime.of(2024, 4, 7, 9, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2024, 4, 7, 10, 0);
        LocalDateTime startTime2 = LocalDateTime.of(2024, 4, 7, 9, 30);
        LocalDateTime endTime2 = LocalDateTime.of(2024, 4, 7, 10, 30);

        List<Meeting> calendar = new ArrayList<>();
        calendar.add(new Meeting(startTime1, endTime1));

        assertTrue(conflictChecker.hasConflict(calendar, startTime2, endTime2));
    }

    @Test
    public void testFindFreeSlotsWithNoMeetings() {
        List<Meeting> employee1Calendar = new ArrayList<>();
        List<Meeting> employee2Calendar = new ArrayList<>();

        List<List<LocalDateTime>> freeSlots = meetingScheduler.findFreeSlots(employee1Calendar, employee2Calendar,
                Duration.ofHours(1));

        assertEquals(1, freeSlots.size());
        assertEquals(LocalDateTime.of(2024, 4, 7, 9, 0), freeSlots.get(0).get(0));
        assertEquals(LocalDateTime.of(2024, 4, 7, 18, 0), freeSlots.get(0).get(1));
    }

    // Additional tests for other scenarios can be added here
}
