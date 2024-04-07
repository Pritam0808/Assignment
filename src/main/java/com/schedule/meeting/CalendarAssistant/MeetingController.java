package com.schedule.meeting.CalendarAssistant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MeetingController {
    private final MeetingScheduler meetingScheduler;
    private final MeetingConflictChecker conflictChecker;

    public MeetingController(MeetingScheduler meetingScheduler, MeetingConflictChecker conflictChecker) {
        this.meetingScheduler = meetingScheduler;
        this.conflictChecker = conflictChecker;
    }

    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleMeeting(@RequestBody MeetingRequest meetingRequest) {
        try {
            List<String> participantIds = meetingRequest.getParticipantIds();
            List<Meeting> employee1Calendar = meetingRequest.getEmployee1Calendar();
            List<Meeting> employee2Calendar = meetingRequest.getEmployee2Calendar();

            // Check if any participant has a conflict
            for (String participantId : participantIds) {
                List<Meeting> calendar;
                if (participantId.equals("employee1")) {
                    calendar = employee1Calendar;
                } else if (participantId.equals("employee2")) {
                    calendar = employee2Calendar;
                } else {
                    return ResponseEntity.badRequest().body("Unknown participant: " + participantId);
                }

                if (conflictChecker.hasConflict(calendar, meetingRequest.getStartTime(), meetingRequest.getEndTime())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Participant with ID " + participantId + " has a meeting conflict.");
                }
            }

            // If no conflicts found, return success response
            return ResponseEntity.ok("No conflicts found. Meeting can be scheduled.");

            // Rest of your code...
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while scheduling the meeting.");
        }
    }
}
