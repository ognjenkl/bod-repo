package com.workforce.bod.assignment.board;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeekAvailabilityTest {

    private static final String MONDAY_NOON = "2023-04-24 12:00:00";
    private static final String SUNDAY_NOON = "2023-04-23 12:00:00";

    @Test
    void givenNull_whenIsWorkingTime_thenFalse() {
        WeekAvailability weekAvailability = new WeekAvailability();
        WeekAvailabilityInterval weekAvailabilityInterval = createMondayWorking8to16();
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        boolean isWorkingTime = weekAvailability.isWorkingTime(null);

        assertFalse(isWorkingTime);
    }

    @Test
    void givenMondayNoon_whenIsWorkingTime_thenTrue() {
        WeekAvailability weekAvailability = new WeekAvailability();
        WeekAvailabilityInterval weekAvailabilityInterval = createMondayWorking8to16();
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        boolean isWorkingTime = weekAvailability.isWorkingTime(
                getTime(MONDAY_NOON));

        assertTrue(isWorkingTime);
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

        assertFalse(isWorkingTime);
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

        assertFalse(isWorkingTime);
    }

    private WeekAvailabilityInterval createSundayNotWorkingAllDayLong() {
        return createWeekAvailabilityInterval(
                DayOfWeek.SUNDAY,
                LocalTime.of(0, 0),
                LocalTime.of(0, 0),
                AvailableToWork.NOT_WORK);
    }

    @Test
    void givenTwoDaysFridayToMondayAndTwoLocalTimesAsWorking_whenAddInterval_thenSundayNoonIsWorking() {
        WeekAvailability weekAvailability = new WeekAvailability();

        weekAvailability.addInterval(
                DayOfWeek.FRIDAY, LocalTime.of(16, 0),
                DayOfWeek.MONDAY, LocalTime.of(8, 0),
                AvailableToWork.WORK);

        boolean isWorkingTime =
                weekAvailability.isWorkingTime(getTime(SUNDAY_NOON));
        assertTrue(isWorkingTime);
    }

    @Test
    void givenOneDayMondayAndTwoLocalTimesAsWorking_whenAddInterval_thenFridayNoonIsWorking() {
        WeekAvailability weekAvailability = new WeekAvailability();

        weekAvailability.addInterval(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                AvailableToWork.WORK);

        boolean isWorkingTime =
                weekAvailability.isWorkingTime(getTime(MONDAY_NOON));
        assertTrue(isWorkingTime);
    }
}
