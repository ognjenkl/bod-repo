package com.workforce.bod;

import com.workforce.bod.assignment.Resource;
import com.workforce.bod.assignment.ResourceAssignmentGant;
import com.workforce.bod.assignment.Skill;
import com.workforce.bod.assignment.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceAssignmentGantTest {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String WORK_END = "2020-01-01 16:00:00";
    public static final String WORK_START = "2020-01-01 08:00:00";
    ResourceAssignmentGant gant;
    Resource resourceForConstruction;
    Task task;

    @BeforeEach
    void setUp() {
        resourceForConstruction = new Resource(Skill.CONSTRUCTION,
                getTime(WORK_START), getTime(WORK_END));
        task = new Task();
        gant = new ResourceAssignmentGant(resourceForConstruction);
    }

    @Test
    void givenNotEligibleTask_whenAddAssignment_thenThrowNotEligibleTaskException() {
        task.addRequiredSkill(Skill.DEMOLITION);
        LocalDateTime startTime = getTime("2020-01-01 10:00:00");
        LocalDateTime endTime = getTime("2020-01-01 11:00:00");

        assertThrows(
                ResourceAssignmentGant.NotEligibleTaskException.class,
                () -> gant.addAssignment(task, startTime, endTime));
    }

    @Test
    void givenTaskAndTime_whenAddAssignment_thenInGant() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime("2020-01-01 10:00:00");
        LocalDateTime endTime = getTime("2020-01-01 11:00:00");
        gant.addAssignment(task, startTime, endTime);

        assertTrue(gant.numberOfTasks() > 0);
    }

    private static LocalDateTime getTime(String time) {
        return LocalDateTime.parse(time, FORMATTER);
    }

    @Test
    void givenTaskAndStartOutOfWorkingTime_whenAddAssignment_thenThrowException() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime("2020-01-01 07:00:00");
        LocalDateTime endTime = getTime("2020-01-01 09:00:00");
        assertThrows(
                ResourceAssignmentGant.NotEligibleTaskException.class,
                () -> gant.addAssignment(task, startTime, endTime));
    }

    @Test
    void givenTaskAndEndOutOfWorkingTime_whenAddAssignment_thenThrowException() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime("2020-01-01 15:00:00");
        LocalDateTime endTime = getTime("2020-01-01 17:00:00");
        assertThrows(
                ResourceAssignmentGant.NotEligibleTaskException.class,
                () -> gant.addAssignment(task, startTime, endTime));
    }
}
