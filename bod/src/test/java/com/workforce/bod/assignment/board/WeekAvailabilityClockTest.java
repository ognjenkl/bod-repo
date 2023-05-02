package com.workforce.bod.assignment.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeekAvailabilityClockTest {

    WeekAvailabilityClock clock;

    @BeforeEach
    void setUp() {
        clock = new WeekAvailabilityClockImpl();
    }

    @Test
    void givenNulls_whenGetLocalDateTime_thenReturnNotNull() {
        assertThrows(IllegalArgumentException.class,
                () -> clock.getLocalDateTime(null, null));
    }

    @Test
    void givenDayOfWeekAndLocalTime_whenGetLocalDateTime_thenExpectedValues() {
        LocalDateTime localDateTime = clock.getLocalDateTime(DayOfWeek.FRIDAY, LocalTime.of(16, 0));

        assertEquals(DayOfWeek.FRIDAY, localDateTime.getDayOfWeek());
        assertEquals(16, localDateTime.getHour());
        assertEquals(0, localDateTime.getMinute());
    }

    @Test
    void givenTwoLocalDateTimesOf4DayInterval_whenGetDayIntervals_thenListOfIntervals() {
        List<WeekDayInterval> weekDayIntervals = clock.getWeekDayIntervals(
                DayOfWeek.FRIDAY, LocalTime.of(16, 0),
                DayOfWeek.MONDAY, LocalTime.of(8, 0));

        assertEquals(4, weekDayIntervals.size());
        assertEquals(DayOfWeek.FRIDAY, weekDayIntervals.get(0).getDayOfWeek());
        assertEquals(LocalTime.of(16, 0), weekDayIntervals.get(0).getStart());
        assertEquals(LocalTime.of(0, 0), weekDayIntervals.get(0).getEnd());
        assertEquals(DayOfWeek.SATURDAY, weekDayIntervals.get(1).getDayOfWeek());
        assertEquals(LocalTime.of(0, 0), weekDayIntervals.get(1).getStart());
        assertEquals(LocalTime.of(0, 0), weekDayIntervals.get(1).getEnd());
        assertEquals(DayOfWeek.SUNDAY, weekDayIntervals.get(2).getDayOfWeek());
        assertEquals(LocalTime.of(0, 0), weekDayIntervals.get(2).getStart());
        assertEquals(LocalTime.of(0, 0), weekDayIntervals.get(2).getEnd());
        assertEquals(DayOfWeek.MONDAY, weekDayIntervals.get(3).getDayOfWeek());
        assertEquals(LocalTime.of(0, 0), weekDayIntervals.get(3).getStart());
        assertEquals(LocalTime.of(8, 0), weekDayIntervals.get(3).getEnd());
    }

    @Test
    void givenTwoDaysOfWeekAndLocalTimes_whenGetLocalDateTimes_thenListOfTwo() {
        List<LocalDateTime> localDateTimes = clock.getLocalDateTimes(
                DayOfWeek.FRIDAY, LocalTime.of(16, 0),
                DayOfWeek.MONDAY, LocalTime.of(8, 0));
        assertEquals(2, localDateTimes.size());
    }

    @Test
    void givenTwoLocalDateTimesOfZeroDaysInterval_whenGetDayIntervals_thenListOfIntervals() {
        List<WeekDayInterval> weekDayIntervals = clock.getWeekDayIntervals(
                DayOfWeek.FRIDAY, LocalTime.of(16, 0),
                DayOfWeek.FRIDAY, LocalTime.of(18, 0));

        assertEquals(1, weekDayIntervals.size());
        assertEquals(DayOfWeek.FRIDAY, weekDayIntervals.get(0).getDayOfWeek());
        assertEquals(LocalTime.of(16, 0), weekDayIntervals.get(0).getStart());
        assertEquals(LocalTime.of(18, 0), weekDayIntervals.get(0).getEnd());
    }

    @Test
    void givenTwoLocalDateTimesOfOneDayInterval_whenGetDayIntervals_thenListOfIntervals() {
        List<WeekDayInterval> weekDayIntervals = clock.getWeekDayIntervals(
                DayOfWeek.FRIDAY, LocalTime.of(16, 0),
                DayOfWeek.SATURDAY, LocalTime.of(15, 0));

        assertEquals(2, weekDayIntervals.size());
        assertEquals(DayOfWeek.FRIDAY, weekDayIntervals.get(0).getDayOfWeek());
        assertEquals(LocalTime.of(16, 0), weekDayIntervals.get(0).getStart());
        assertEquals(LocalTime.of(0, 0), weekDayIntervals.get(0).getEnd());
        assertEquals(DayOfWeek.SATURDAY, weekDayIntervals.get(1).getDayOfWeek());
        assertEquals(LocalTime.of(0, 0), weekDayIntervals.get(1).getStart());
        assertEquals(LocalTime.of(15, 0), weekDayIntervals.get(1).getEnd());
    }

    @Test
    void givenTwoLocalDateTimesOfZeroDayInterval_whenGetDayIntervals_thenListOfIntervals() {
        List<WeekDayInterval> weekDayIntervals = clock.getWeekDayIntervals(
                DayOfWeek.FRIDAY, LocalTime.of(0, 0),
                DayOfWeek.FRIDAY, LocalTime.of(0, 0));

        assertEquals(1, weekDayIntervals.size());
        assertEquals(DayOfWeek.FRIDAY, weekDayIntervals.get(0).getDayOfWeek());
        assertEquals(LocalTime.of(0, 0), weekDayIntervals.get(0).getStart());
        assertEquals(LocalTime.of(0, 0), weekDayIntervals.get(0).getEnd());
        System.out.println(weekDayIntervals.get(0));
    }

}