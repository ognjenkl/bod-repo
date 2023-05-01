package com.workforce.bod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeekAvailability {

    List<AvailabilityInterval> availabilityIntervals = new ArrayList<>();
    public boolean canWorkAt(LocalDateTime time) {
        for(AvailabilityInterval availabilityInterval : availabilityIntervals) {
            if (availabilityInterval.availableToWork(time)) {
                return true;
            }
        }
        return false;
    }

    public void addWorkInterval(AvailabilityInterval availabilityInterval) {
        availabilityIntervals.add(availabilityInterval);
    }
}
