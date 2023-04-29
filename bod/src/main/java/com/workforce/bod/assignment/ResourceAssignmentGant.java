package com.workforce.bod.assignment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResourceAssignmentGant {

    private final Resource resource;
    private final List<Assignment> assignments = new ArrayList<>();

    public ResourceAssignmentGant(Resource resource) {
        this.resource = resource;
    }

//    public void addAssignmentWith(Task task) {
//        if (isResourceEligibleFor(task)) {
//            assignments.add(new Assignment(task, resource));
//        } else {
//            throw new NotEligibleTaskException();
//        }
//    }

    private boolean isResourceEligibleFor(Task task,
                                          LocalDateTime start,
                                          LocalDateTime end) {
        boolean isSkillRequired = isSkillRequired(task);
        boolean isStartInWorkingHours = isInWorkingHours(start);
        boolean isEndInWorkingHours = isInWorkingHours(end);
        return isSkillRequired && isStartInWorkingHours && isEndInWorkingHours;
    }

    private boolean isInWorkingHours(LocalDateTime start) {
        return (resource.getWorkStart().isBefore(start)
                || resource.getWorkStart().isEqual(start))
                && (resource.getWorkEnd().isAfter(start)
                || resource.getWorkEnd().isEqual(start));
    }

    private boolean isSkillRequired(Task task) {
        return task.getRequiredSkills()
                .contains(resource.getSkill());
    }

    public Integer numberOfTasks() {
        return assignments.size();
    }

    public void addAssignment(Task task, LocalDateTime start, LocalDateTime end) {
        if (isResourceEligibleFor(task, start, end)) {
            assignments.add(new Assignment(task, resource, start, end));
        } else {
            throw new NotEligibleTaskException();
        }
    }

    public static class NotEligibleTaskException extends RuntimeException {
    }
}
