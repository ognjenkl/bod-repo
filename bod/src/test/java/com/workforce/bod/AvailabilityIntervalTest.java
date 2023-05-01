package com.workforce.bod;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvailabilityIntervalTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String MONDAY_WORKING_HOUR = "2023-04-24 12:00:00";
    private static final String MONDAY_NON_WORKING_HOUR = "2023-04-24 06:00:00";
    private static final String MONDAY_EIGHT = "2023-04-24 08:00:00";
    private static final String MONDAY_SIXTEEN = "2023-04-24 16:00:00";

    @Test
    void givenNull_whenCovers_thenFalse() {
        AvailabilityInterval availabilityInterval =
                new AvailabilityInterval(null, null);

        assertFalse(availabilityInterval.availableToWork(null));
    }

    @Test
    void givenTime_whenMondayWorkingHours_thenTrue() {
        WeekDayInterval weekDayInterval = createWorkDayInterval(DayOfWeek.MONDAY);
        AvailabilityInterval availabilityInterval = new AvailabilityInterval(
                AvailableToWork.WORK, weekDayInterval);
        LocalDateTime dateTime = getTime(MONDAY_WORKING_HOUR);

        assertTrue(availabilityInterval.availableToWork(dateTime));
    }

    @Test
    void givenTimeNotInInterval_whenCovers_thenFalse() {
        AvailabilityInterval availabilityInterval = new AvailabilityInterval(
                AvailableToWork.WORK, createWorkDayInterval(DayOfWeek.MONDAY));
        LocalDateTime dateTime = getTime(MONDAY_NON_WORKING_HOUR).plusHours(1);

        assertFalse(availabilityInterval.availableToWork(dateTime));
    }

    @Test
    void giveTime_whenAtTheStartOfWorkingHours_thenAvailableToWork() {
        AvailabilityInterval availabilityInterval = new AvailabilityInterval(
                AvailableToWork.WORK, createWorkDayInterval(DayOfWeek.MONDAY));
        LocalDateTime dateTime = getTime(MONDAY_EIGHT);

        assertTrue(availabilityInterval.availableToWork(dateTime));
    }

    @Test
    void giveTimeAtTheEndOfInterval_whenCovers_thenFalse() {
        AvailabilityInterval availabilityInterval = new AvailabilityInterval(
                AvailableToWork.WORK, createWorkDayInterval(DayOfWeek.MONDAY));
        LocalDateTime dateTime = getTime(MONDAY_SIXTEEN);

        assertFalse(availabilityInterval.availableToWork(dateTime));
    }

    @Test
    void givenTuesdayTimeInInterval_whenCovers_thenFalse() {
        AvailabilityInterval availabilityInterval = new AvailabilityInterval(
                AvailableToWork.WORK, createWorkDayInterval(DayOfWeek.MONDAY));

        LocalDateTime dateTime = getTime(MONDAY_WORKING_HOUR).plusDays(1);

        assertFalse(availabilityInterval.availableToWork(dateTime));
    }

    @Test
    void givenTuesdayTimeNotInInterval_whenCovers_thenFalse() {
        AvailabilityInterval availabilityInterval = new AvailabilityInterval(
                AvailableToWork.WORK, createWorkDayInterval(DayOfWeek.TUESDAY));

        LocalDateTime dateTime = getTime(MONDAY_NON_WORKING_HOUR).plusDays(1);

        assertFalse(availabilityInterval.availableToWork(dateTime));
    }

    private WeekDayInterval createWorkDayInterval(DayOfWeek dayOfWeek) {
        return new WeekDayInterval(dayOfWeek,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0));
    }

    private LocalDateTime getTime(String dateTime) {
        return LocalDateTime.parse(dateTime, FORMATTER);
    }

}