package com.schedule.meeting.CalendarAssistant;

public enum MeetingParticipant {
    EMPLOYEE1("employee1"),
    EMPLOYEE2("employee2");

    private final String id;

    MeetingParticipant(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
