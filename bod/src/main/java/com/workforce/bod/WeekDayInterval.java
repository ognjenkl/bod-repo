package com.workforce.bod;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record WeekDayInterval(DayOfWeek dayOfWeek,
                              LocalTime start,
                              LocalTime end) {

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
