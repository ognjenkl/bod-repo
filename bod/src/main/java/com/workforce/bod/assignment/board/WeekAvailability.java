package com.workforce.bod.assignment.board;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WeekAvailability {

    List<WeekAvailabilityInterval> weekAvailabilityIntervals = new ArrayList<>();

    public boolean isWorkingTime(LocalDateTime time) {
        for (WeekAvailabilityInterval weekAvailabilityInterval : weekAvailabilityIntervals) {
            if (weekAvailabilityInterval.availableToWork(time)) {
                return true;
            }
        }
        return false;
    }

    public void addWorkInterval(WeekAvailabilityInterval weekAvailabilityInterval) {
        weekAvailabilityIntervals.add(weekAvailabilityInterval);
    }

    public void addInterval(DayOfWeek startDay, LocalTime start,
                            DayOfWeek endDay, LocalTime end,
                            AvailableToWork availableToWork) {

        WeekAvailabilityClockImpl clock = new WeekAvailabilityClockImpl();

        List<WeekDayInterval> weekDayIntervals = clock.getWeekDayIntervals(startDay, start, endDay, end);
        for (WeekDayInterval weekDayInterval : weekDayIntervals) {
            WeekAvailabilityInterval weekAvailabilityInterval =
                    new WeekAvailabilityInterval(availableToWork, weekDayInterval);
            weekAvailabilityIntervals.add(weekAvailabilityInterval);
        }
    }

    public void addInterval(DayOfWeek dayOfWeek,
                            LocalTime start, LocalTime end,
                            AvailableToWork work) {
        addInterval(dayOfWeek, start, dayOfWeek, end, work);
    }
}
