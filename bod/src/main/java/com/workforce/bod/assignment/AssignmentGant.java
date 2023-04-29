package com.workforce.bod.assignment;

import java.util.ArrayList;
import java.util.List;

public class AssignmentGant {

    private final Resource resource;
    private final List<Assignment> assignments = new ArrayList<>();

    public AssignmentGant(Resource resource) {
        this.resource = resource;
    }

    public void addTask(Task task) {
        if (isResourceEligibleFor(task)) {
            assignments.add(new Assignment(task, resource));
        } else {
            throw new NotEligibleTaskException();
        }
    }

    private boolean isResourceEligibleFor(Task task) {
        if (task.getRequiredSkills().isEmpty())
            return true;

        return task.getRequiredSkills()
                .contains(resource.getSkill());
    }

    public Integer numberOfTasks() {
        return assignments.size();
    }

    public static class NotEligibleTaskException extends RuntimeException {
    }
}
