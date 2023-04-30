package com.workforce.bod.assignment;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Assignment {

    private final Task task;
    private final Resource resource;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Assignment(Task task, Resource resource,
                      LocalDateTime start, LocalDateTime end) {
        this.task = task;
        this.resource = resource;
        this.start = start;
        this.end = end;

        if (end.isBefore(start)) {
            throw new TimeOrderException();
        }
    }

    public boolean isInTimeInterval(LocalDateTime time) {
        return (start.isBefore(time) || start.isEqual(time))
                && (end.isAfter(time) || end.isEqual(time));
    }

    public boolean isInTimeCollision(Assignment assignment) {
        return isInTimeInterval(assignment.getStart())
                || isInTimeInterval(assignment.getEnd());
    }

    public static class TimeOrderException extends RuntimeException {
    }
}
