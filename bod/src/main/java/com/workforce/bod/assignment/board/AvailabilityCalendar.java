package com.workforce.bod.assignment.board;

import java.time.LocalDateTime;

public class AvailabilityCalendar {
    private WeekAvailability weekAvailability = new WeekAvailability();

    public boolean isAvailableToWork(LocalDateTime time) {
        if (time == null) {
            return false;
        }

        return weekAvailability.isWorkingTime(time);
    }

    public void addWeekAvailability(WeekAvailability weekAvailability) {
        this.weekAvailability = weekAvailability;
    }
}
