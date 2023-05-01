package com.workforce.bod.assignment.board;

import com.workforce.bod.assignment.board.exception.TimeOrderException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class WeekDayInterval {
    private final DayOfWeek dayOfWeek;
    private final LocalTime start;
    private final LocalTime end;

    public WeekDayInterval(DayOfWeek dayOfWeek,
                           LocalTime start,
                           LocalTime end) {
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;

        if (end.isBefore(start)) {
            throw new TimeOrderException();
        }
    }

    public boolean covers(LocalDateTime time) {
        if (time == null)
            return false;

        return dayOfWeek.equals(time.getDayOfWeek())
                && isBetweenIncludingStart(time);
    }

    private boolean isBetweenIncludingStart(LocalDateTime time) {
        return (start.isBefore(time.toLocalTime())
                || start.equals(time.toLocalTime()))
                && end.isAfter(time.toLocalTime());
    }
}
