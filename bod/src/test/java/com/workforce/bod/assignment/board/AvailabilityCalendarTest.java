package com.workforce.bod.assignment.board;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvailabilityCalendarTest {

    private static final String MONDAY_NOON_WORKING_HOUR = "2023-04-24 12:00:00";
    private static final String MONDAY_SIX_NON_WORKING_HOUR = "2023-04-24 06:00:00";
    private static final String TUESDAY_WORKING_HOUR = "2023-04-25 12:00:00";

    @Test
    void givenNull_whenIsWorkingTime_thenFalse() {
        AvailabilityCalendar availabilityCalendar = new AvailabilityCalendar();
        assertFalse(availabilityCalendar.isAvailableToWork(null));
    }

    @Test
    void givenTimeInput_whenTimeInputIsInMondayWorkingTime_thenTrue() {
        WeekDayInterval weekDayInterval = new WeekDayInterval(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0));
        WeekAvailabilityInterval weekAvailabilityInterval =
                new WeekAvailabilityInterval(AvailableToWork.WORK, weekDayInterval);
        WeekAvailability weekAvailability = new WeekAvailability();
        weekAvailability.addWorkInterval(weekAvailabilityInterval);
        AvailabilityCalendar availabilityCalendar = new AvailabilityCalendar();
        availabilityCalendar.addWeekAvailability(weekAvailability);
        LocalDateTime dateTime = getTime(MONDAY_NOON_WORKING_HOUR);

        assertTrue(availabilityCalendar.isAvailableToWork(dateTime));
    }

    @Test
    void givenMondayNonWorkingTime_whenIsWorkingTime_thenFalse() {
        AvailabilityCalendar availabilityCalendar = new AvailabilityCalendar();
        WeekAvailability weekAvailability = new WeekAvailability();
        availabilityCalendar.addWeekAvailability(weekAvailability);
        LocalDateTime dateTime = getTime(MONDAY_SIX_NON_WORKING_HOUR).plusHours(1);

        assertFalse(availabilityCalendar.isAvailableToWork(dateTime));
    }

    @Test
    void givenTuesdayWorkingTime_whenIsWorkingTime_thenFalse() {
        AvailabilityCalendar availabilityCalendar = new AvailabilityCalendar();
        WeekAvailability weekAvailability = new WeekAvailability();
        availabilityCalendar.addWeekAvailability(weekAvailability);
        LocalDateTime dateTime = getTime(TUESDAY_WORKING_HOUR);

        assertFalse(availabilityCalendar.isAvailableToWork(dateTime));
    }

    private LocalDateTime getTime(String dateTime) {
        return LocalDateTime.parse(
                dateTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}