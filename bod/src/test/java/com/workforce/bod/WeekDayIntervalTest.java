package com.workforce.bod;

import com.workforce.bod.exception.TimeOrderException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class WeekDayIntervalTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String MONDAY_NOON = "2023-04-24 12:00:00";
    private static final String MONDAY_SIX = "2023-04-24 06:00:00";
    private static final String MONDAY_EIGHT = "2023-04-24 08:00:00";
    private static final String MONDAY_SIXTEEN = "2023-04-24 16:00:00";

    @Test
    void givenNullForTime_whenCoversNullTime_thenFalse() {
        WeekDayInterval weekDayInterval = new WeekDayInterval(
                DayOfWeek.MONDAY, null, null);
        assertFalse(weekDayInterval.covers(null));
    }

    @Test
    void givenMondayTwelve_whenCovers_thenTrue() {
        WeekDayInterval weekDayInterval = new WeekDayInterval(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0));

        assertTrue(weekDayInterval.covers(getTime(MONDAY_NOON)));
    }

    @Test
    void givenMondaySix_whenCovers_thenFalse() {
        WeekDayInterval weekDayInterval = new WeekDayInterval(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0));

        assertFalse(weekDayInterval.covers(getTime(MONDAY_SIX)));
    }

    @Test
    void givenMondayEight_whenCovers_thenTrue() {
        WeekDayInterval weekDayInterval = new WeekDayInterval(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0));

        assertTrue(weekDayInterval.covers(getTime(MONDAY_EIGHT).plusHours(2)));
    }

    @Test
    void givenMondaySixteen_whenCovers_thenFalse() {
        WeekDayInterval weekDayInterval = new WeekDayInterval(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0));

        assertFalse(weekDayInterval.covers(getTime(MONDAY_SIXTEEN)));
    }

    @Test
    void givenEndBeforeStart_whenCovers_thenThrowsException() {
        assertThrows(TimeOrderException.class,
                () -> new WeekDayInterval(
                        DayOfWeek.MONDAY,
                        LocalTime.of(16, 0),
                        LocalTime.of(8, 0)));
    }

    private LocalDateTime getTime(String dateTime) {
        return LocalDateTime.parse(dateTime, FORMATTER);
    }
}