package com.workforce.bod.assignment;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class Assignment {

    private final Task task;
    private final Resource resource;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public boolean isInTimeInterval(LocalDateTime time) {
        return (start.isBefore(time) || start.isEqual(time))
                && (end.isAfter(time) || end.isEqual(time));
    }

    public boolean isInTimeCollision(Assignment assignment) {
        return isInTimeInterval(assignment.getStart())
                || isInTimeInterval(assignment.getEnd());
    }
}
