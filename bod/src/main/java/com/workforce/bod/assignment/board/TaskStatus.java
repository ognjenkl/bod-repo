package com.workforce.bod.assignment.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@RequiredArgsConstructor
@Getter
public class TaskStatus {

    private final String current;
    private final List<String> transitions;

    public Boolean canTransitionTo(TaskStatus status) {
        return transitions.contains(status.getCurrent());
    }

    public enum Status {
        OPEN, SCHEDULED, CANCELLED, DISPATCHED
    }
}
