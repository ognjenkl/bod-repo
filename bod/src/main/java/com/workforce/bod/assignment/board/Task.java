package com.workforce.bod.assignment.board;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Task {

    private final List<Skill> requiredSkills = new ArrayList<>();
    private TaskStatus status;

    public Task(TaskStatus status) {
        this.status = status;
    }

    public void addRequiredSkill(Skill skill) {
        requiredSkills.add(skill);
    }

    public void transitionTo(TaskStatus transitionStatus) {
        if (status.canTransitionTo(transitionStatus)) {
            this.status = transitionStatus;
        } else {
            throw new IllegalStateException("Cannot transition from " + status + " to " + transitionStatus);
        }
    }
}
