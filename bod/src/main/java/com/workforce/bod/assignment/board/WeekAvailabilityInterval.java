package com.workforce.bod.assignment.board;

import java.time.LocalDateTime;

public record WeekAvailabilityInterval(AvailableToWork availableToWork,
                                       WeekDayInterval weekDayInterval) {

    public boolean availableToWork(LocalDateTime localDateTime) {
        return AvailableToWork.WORK.equals(availableToWork)
                && weekDayInterval.covers(localDateTime);
    }
}
