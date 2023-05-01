package com.workforce.bod;

import java.time.LocalDateTime;

public record AvailabilityInterval(
        AvailableToWork availableToWork, WeekDayInterval weekDayInterval) {

    public boolean availableToWork(LocalDateTime localDateTime) {
        return AvailableToWork.WORK.equals(availableToWork)
                && weekDayInterval.covers(localDateTime);
    }
}
