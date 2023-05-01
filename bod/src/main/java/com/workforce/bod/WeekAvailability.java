package com.workforce.bod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeekAvailability {

    List<WeekAvailabilityInterval> weekAvailabilityIntervals = new ArrayList<>();
    public boolean canWorkAt(LocalDateTime time) {
        for(WeekAvailabilityInterval weekAvailabilityInterval : weekAvailabilityIntervals) {
            if (weekAvailabilityInterval.availableToWork(time)) {
                return true;
            }
        }
        return false;
    }

    public void addWorkInterval(WeekAvailabilityInterval weekAvailabilityInterval) {
        weekAvailabilityIntervals.add(weekAvailabilityInterval);
    }
}
