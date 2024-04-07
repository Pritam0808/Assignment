package com.schedule.meeting.CalendarAssistant;
import java.time.LocalDateTime;
import java.util.List;

public class MeetingRequest {
    private List<String> participantIds;
    private List<Meeting> employee1Calendar;
    private List<Meeting> employee2Calendar;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Getters and setters for all fields
    

    public List<Meeting> getEmployee1Calendar() {
        return employee1Calendar;
    }

    public List<String> getParticipantIds() {
		return participantIds;
	}

	public void setParticipantIds(List<String> participantIds) {
		this.participantIds = participantIds;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public void setEmployee1Calendar(List<Meeting> employee1Calendar) {
        this.employee1Calendar = employee1Calendar;
    }

    public List<Meeting> getEmployee2Calendar() {
        return employee2Calendar;
    }

    public void setEmployee2Calendar(List<Meeting> employee2Calendar) {
        this.employee2Calendar = employee2Calendar;
    }
}
