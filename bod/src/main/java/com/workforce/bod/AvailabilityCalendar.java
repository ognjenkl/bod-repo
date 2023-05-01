package com.workforce.bod;

import java.time.LocalDateTime;

public class AvailabilityCalendar {
    private WeekAvailability weekAvailability = new WeekAvailability();

    public boolean isAvailableToWork(LocalDateTime time) {
        if (time == null) {
            return false;
        }

        return weekAvailability.canWorkAt(time);
    }

    public void addWeekAvailability(WeekAvailability weekAvailability) {
        this.weekAvailability = weekAvailability;
    }
}
