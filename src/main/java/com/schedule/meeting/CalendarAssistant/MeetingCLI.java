package com.schedule.meeting.CalendarAssistant;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MeetingCLI {

    private final Scanner scanner;
    private final MeetingScheduler meetingScheduler;
    private final MeetingConflictChecker conflictChecker;

    public MeetingCLI() {
        this.scanner = new Scanner(System.in);
        this.meetingScheduler = new MeetingScheduler();
        this.conflictChecker = new MeetingConflictChecker();
    }

    public void run() {
        LocalDateTime startTime = promptForValidTime("Enter meeting start time (HH:mm):");

        LocalDateTime endTime;
        do {
            endTime = promptForValidTime("Enter meeting end time (HH:mm):");
            if (endTime.isBefore(startTime)) {
                System.out.println("End time cannot be before start time. Please enter a valid end time.");
            }
        } while (endTime.isBefore(startTime));

        // Define employee calendars
        List<Meeting> employee1Calendar = new ArrayList<>();
        employee1Calendar.add(new Meeting(LocalDateTime.of(2024, 4, 7, 9, 0), LocalDateTime.of(2024, 4, 7, 10, 0)));
        employee1Calendar.add(new Meeting(LocalDateTime.of(2024, 4, 7, 12, 0), LocalDateTime.of(2024, 4, 7, 13, 0)));

        List<Meeting> employee2Calendar = new ArrayList<>();
        employee2Calendar.add(new Meeting(LocalDateTime.of(2024, 4, 7, 10, 0), LocalDateTime.of(2024, 4, 7, 11, 0)));
        employee2Calendar.add(new Meeting(LocalDateTime.of(2024, 4, 7, 13, 0), LocalDateTime.of(2024, 4, 7, 14, 0)));

        // Schedule the meeting
        List<List<LocalDateTime>> freeSlots = meetingScheduler.findFreeSlots(employee1Calendar, employee2Calendar, Duration.ofMinutes(30));
        System.out.println("Free slots:");
        for (List<LocalDateTime> slot : freeSlots) {
            System.out.println(slot.get(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " - " +
                    slot.get(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        if (conflictChecker.hasConflict(employee1Calendar, startTime, endTime) ||
                conflictChecker.hasConflict(employee2Calendar, startTime, endTime)) {
            System.out.println("Conflict slots:");
            System.out.println(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " - " +
                    endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
    }


    private LocalDateTime promptForValidTime(String message) {
        LocalDateTime time = null;
        boolean isValid = false;
        do {
            System.out.println(message);
            String input = scanner.nextLine();
            try {
                time = parseTimeInput(input);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid time format. Please enter the time in HH:mm format.");
            }
        } while (!isValid);
        return time;
    }

    private LocalDateTime parseTimeInput(String input) {
        while (true) {
            String[] parts = input.split(":");
            if (parts.length != 2) {
                System.out.println("Invalid time format. Please enter the time in HH:mm format.");
                input = scanner.nextLine(); // Ask for input again
                continue; // Continue to the next iteration of the loop
            }
            
            int hour, minute;
            try {
                hour = Integer.parseInt(parts[0]);
                minute = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid time format. Please enter the time in HH:mm format.");
                input = scanner.nextLine(); // Ask for input again
                continue; // Continue to the next iteration of the loop
            }

            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                System.out.println("Invalid time format. Hours should be between 0 and 23, and minutes should be between 0 and 59.");
                input = scanner.nextLine(); // Ask for input again
                continue; // Continue to the next iteration of the loop
            }

            // If input is valid, return LocalDateTime
            return LocalDateTime.now().withHour(hour).withMinute(minute).withSecond(0).withNano(0);
        }
    }


    public static void main(String[] args) {
        MeetingCLI cli = new MeetingCLI();
        cli.run();
    }
}
