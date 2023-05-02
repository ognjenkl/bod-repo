package com.workforce.bod.assignment.board;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface WeekAvailabilityClock {

    LocalDateTime getLocalDateTime(DayOfWeek dayOfWeek, LocalTime localTime);

    List<WeekDayInterval> getWeekDayIntervals(
            DayOfWeek startDay, LocalTime startTime,
            DayOfWeek endDay, LocalTime endTime);

    List<LocalDateTime> getLocalDateTimes(DayOfWeek startDay, LocalTime startTime,
                                          DayOfWeek endDay, LocalTime endTime);
}
