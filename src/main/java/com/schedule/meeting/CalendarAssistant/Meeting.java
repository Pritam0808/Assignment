package com.schedule.meeting.CalendarAssistant;

import java.time.LocalDateTime;

public class Meeting {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Meeting(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
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

    // Getters and setters
    
}
