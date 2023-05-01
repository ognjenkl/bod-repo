package com.workforce.bod.assignment.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class WeekAvailabilityTest {

    private static final String MONDAY_NOON = "2023-04-24 12:00:00";

    @Test
    void givenNull_whenIsWorkingTime_thenFalse() {
        WeekAvailability weekAvailability = new WeekAvailability();
        WeekAvailabilityInterval weekAvailabilityInterval = createMondayWorking8to16();
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        boolean isWorkingTime = weekAvailability.isWorkingTime(null);

        Assertions.assertFalse(isWorkingTime);
    }

    @Test
    void givenMondayNoon_whenIsWorkingTime_thenTrue() {
        WeekAvailability weekAvailability = new WeekAvailability();
        WeekAvailabilityInterval weekAvailabilityInterval = createMondayWorking8to16();
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        boolean isWorkingTime = weekAvailability.isWorkingTime(
                getTime(MONDAY_NOON));

        Assertions.assertTrue(isWorkingTime);
    }

    private LocalDateTime getTime(String dateTime) {
        return LocalDateTime.parse(
                dateTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    void givenMondaySix_whenIsNotWorkingTime_thenFalse() {
        WeekAvailability weekAvailability = new WeekAvailability();
        WeekAvailabilityInterval weekAvailabilityInterval = createMondayWorking8to16();
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        boolean isWorkingTime = weekAvailability.isWorkingTime(
                getTime(MONDAY_NOON).minusHours(6));

        Assertions.assertFalse(isWorkingTime);
    }

    private WeekAvailabilityInterval createMondayWorking8to16() {
        return createWeekAvailabilityInterval(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                AvailableToWork.WORK);
    }

    private WeekAvailabilityInterval createWeekAvailabilityInterval(
            DayOfWeek dayOfWeek,
            LocalTime start,
            LocalTime end,
            AvailableToWork availableToWork) {
        WeekDayInterval weekDayInterval = new WeekDayInterval(
                dayOfWeek, start, end);
        return new WeekAvailabilityInterval(availableToWork, weekDayInterval);
    }

    @Test
    void givenSundayNoon_whenIsNotWorking_thenFalse() {
        WeekAvailability weekAvailability = new WeekAvailability();

        WeekAvailabilityInterval weekAvailabilityInterval = createMondayWorking8to16();
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        weekAvailabilityInterval = createSundayNotWorkingAllDayLong();
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        boolean isWorkingTime = weekAvailability.isWorkingTime(
                getTime(MONDAY_NOON).minusDays(1));

        Assertions.assertFalse(isWorkingTime);
    }

    private WeekAvailabilityInterval createSundayNotWorkingAllDayLong() {
        return  createWeekAvailabilityInterval(
                DayOfWeek.SUNDAY,
                LocalTime.of(0, 0),
                LocalTime.of(0, 0),
                AvailableToWork.NOT_WORK);
    }
}