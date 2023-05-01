package com.workforce.bod.assignment;

import com.workforce.bod.AvailabilityCalendar;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Resource {

    private final Skill skill;
    private final AvailabilityCalendar availabilityCalendar;

    public Resource(Skill skill, AvailabilityCalendar availabilityCalendar) {
        this.skill = skill;
        this.availabilityCalendar = availabilityCalendar;
    }

    public boolean isInWorkTime(LocalDateTime time) {
        return availabilityCalendar.isAvailableToWork(time);
    }
}
