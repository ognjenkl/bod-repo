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
                && isBetweenIncludingStartAndExcludingEnd(time);
    }

    private boolean isBetweenIncludingStartAndExcludingEnd(LocalDateTime time) {
        return isAfterStartIncluding(time) && isBeforeEndExcluding(time);
    }

    private boolean isBeforeEndExcluding(LocalDateTime time) {
        if (LocalTime.of(0, 0).equals(end)) {
            LocalTime inclusiveEnd = end.minusNanos(1);
            return inclusiveEnd.isAfter(time.toLocalTime())
                    || inclusiveEnd.equals(time.toLocalTime());
        } else {
            return end.isAfter(time.toLocalTime());
        }
    }

    private boolean isAfterStartIncluding(LocalDateTime time) {
        return start.isBefore(time.toLocalTime())
                || start.equals(time.toLocalTime());
    }
}
