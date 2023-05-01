package com.workforce.bod.assignment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardLane {

    private final Resource resource;
    private final List<Assignment> assignments = new ArrayList<>();

    public BoardLane(Resource resource) {
        this.resource = resource;
    }

    private boolean isEligible(Assignment assignment) {
        boolean isSkillRequired = isResourceSkillAmongRequired(assignment.getTask());
        boolean isInWorkingHours = isInWorkingHours(assignment);
        boolean isInCollisionWithOtherTasks = isInCollisionWithOtherTasks(assignment);
        return isSkillRequired
                && isInWorkingHours
                && !isInCollisionWithOtherTasks;
    }

    private boolean isInWorkingHours(Assignment assignment) {
        boolean isInWorkingHours = isInWorkingHours(assignment.getStart())
                && isInWorkingHours(assignment.getEnd());
        if (!isInWorkingHours) {
            throw new WorkingHoursException();
        }
        return true;
    }

    private boolean isInCollisionWithOtherTasks(Assignment assignment) {
        boolean isInCollisionWithOtherTasks = assignments
                .stream()
                .anyMatch(a -> a.isInTimeCollision(assignment));
        if (isInCollisionWithOtherTasks) {
            throw new TimeCollisionException();
        }
        return false;
    }

    private boolean isInWorkingHours(LocalDateTime localDateTime) {
        return resource.getAvailabilityCalendar().isAvailableToWork(localDateTime);
    }

    private boolean isResourceSkillAmongRequired(Task task) {
        boolean isResourceSkillRequired = task.getRequiredSkills()
                .contains(resource.getSkill());
        if (!isResourceSkillRequired) {
            throw new SkillNotRequiredException();
        }
        return true;
    }

    public Integer numberOfTasks() {
        return assignments.size();
    }

    public void addAssignment(Task task, LocalDateTime start, LocalDateTime end) {
        Assignment assignment = new Assignment(task, resource, start, end);
        if (isEligible(assignment)) {
            assignments.add(assignment);
        } else {
            throw new NotEligibleTaskException();
        }
    }

    public static class NotEligibleTaskException extends RuntimeException {
    }

    public static class TimeCollisionException extends RuntimeException {
    }

    public static class WorkingHoursException extends RuntimeException {
    }

    public static class SkillNotRequiredException extends RuntimeException {
    }
}
