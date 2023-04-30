package com.workforce.bod;

import com.workforce.bod.assignment.BoardLane;
import com.workforce.bod.assignment.Resource;
import com.workforce.bod.assignment.Skill;
import com.workforce.bod.assignment.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardLaneTest {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String WORK_END = "2020-01-01 16:00:00";
    public static final String WORK_START = "2020-01-01 08:00:00";
    BoardLane boardLane;
    Resource resourceForConstruction;
    Task task;

    @BeforeEach
    void setUp() {
        resourceForConstruction = new Resource(Skill.CONSTRUCTION,
                getTime(WORK_START), getTime(WORK_END));
        task = new Task();
        boardLane = new BoardLane(resourceForConstruction);
    }

    @Test
    void givenNotEligibleTask_whenAddAssignment_thenThrowNotEligibleTaskException() {
        task.addRequiredSkill(Skill.DEMOLITION);
        LocalDateTime startTime = getTime("2020-01-01 10:00:00");
        LocalDateTime endTime = getTime("2020-01-01 11:00:00");

        assertThrows(
                BoardLane.SkillNotRequiredException.class,
                () -> boardLane.addAssignment(task, startTime, endTime));
    }

    @Test
    void givenTaskAndTime_whenAddAssignment_thenInBoardLane() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime("2020-01-01 10:00:00");
        LocalDateTime endTime = getTime("2020-01-01 11:00:00");
        boardLane.addAssignment(task, startTime, endTime);

        assertTrue(boardLane.numberOfTasks() > 0);
    }

    private static LocalDateTime getTime(String time) {
        return LocalDateTime.parse(time, FORMATTER);
    }

    @Test
    void givenTask_whenStartIsOutOfWorkingHours_thenThrowException() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime("2020-01-01 07:00:00");
        LocalDateTime endTime = getTime("2020-01-01 09:00:00");
        assertThrows(
                BoardLane.WorkingHoursException.class,
                () -> boardLane.addAssignment(task, startTime, endTime));
    }

    @Test
    void givenTask_whenEndIsOutOfWorkingHours_thenThrowException() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime("2020-01-01 15:00:00");
        LocalDateTime endTime = getTime("2020-01-01 17:00:00");
        assertThrows(
                BoardLane.WorkingHoursException.class,
                () -> boardLane.addAssignment(task, startTime, endTime));
    }

    @Test
    void givenNewTask_whenStartOverlapsWithExistingTasks_thenThrowException() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime("2020-01-01 10:00:00");
        LocalDateTime endTime = getTime("2020-01-01 11:00:00");
        boardLane.addAssignment(task, startTime, endTime);

        Task newTask = new Task();
        newTask.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime newStartTime = getTime("2020-01-01 10:30:00");
        LocalDateTime newEndTime = getTime("2020-01-01 11:30:00");

        assertThrows(
                BoardLane.TimeCollisionException.class,
                () -> boardLane.addAssignment(newTask, newStartTime, newEndTime));
    }

    @Test
    void givenNewTask_whenEndOverlapsWithExistingTasks_thenThrowException() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime("2020-01-01 10:00:00");
        LocalDateTime endTime = getTime("2020-01-01 11:00:00");
        boardLane.addAssignment(task, startTime, endTime);

        Task newTask = new Task();
        newTask.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime newStartTime = getTime("2020-01-01 09:30:00");
        LocalDateTime newEndTime = getTime("2020-01-01 10:30:00");

        assertThrows(
                BoardLane.TimeCollisionException.class,
                () -> boardLane.addAssignment(newTask, newStartTime, newEndTime));
    }
}
