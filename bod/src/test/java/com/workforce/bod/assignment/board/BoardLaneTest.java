package com.workforce.bod.assignment.board;

import com.workforce.bod.assignment.board.exception.SkillNotRequiredException;
import com.workforce.bod.assignment.board.exception.TimeCollisionException;
import com.workforce.bod.assignment.board.exception.WorkingHoursException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardLaneTest {

    public static final String MONDAY_ELEVEN = "2023-04-24 11:00:00";
    public static final String MONDAY_NINE_THIRTY = "2023-04-24 09:30:00";
    public static final String MONDAY_TEN_THIRTY = "2023-04-24 10:30:00";
    public static final String MONDAY_TEN = "2023-04-24 10:00:00";
    public static final String MONDAY_ELEVEN_THIRTY = "2023-04-24 11:30:00";
    BoardLane boardLane;
    Resource resourceForConstruction;
    Task task;

    @BeforeEach
    void setUp() {
        WeekDayInterval weekDayInterval = new WeekDayInterval(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0));
        WeekAvailabilityInterval weekAvailabilityInterval =
                new WeekAvailabilityInterval(AvailableToWork.WORK, weekDayInterval);
        WeekAvailability weekAvailability = new WeekAvailability();
        weekAvailability.addWorkInterval(weekAvailabilityInterval);
        AvailabilityCalendar availabilityCalendar = new AvailabilityCalendar();
        availabilityCalendar.addWeekAvailability(weekAvailability);
        resourceForConstruction = new Resource(Skill.CONSTRUCTION, availabilityCalendar);
        task = new Task();
        boardLane = new BoardLane(resourceForConstruction);
    }

    @Test
    void givenNotEligibleTask_whenAddAssignment_thenThrowNotEligibleTaskException() {
        task.addRequiredSkill(Skill.DEMOLITION);
        LocalDateTime startTime = getTime(MONDAY_TEN);
        LocalDateTime endTime = getTime(MONDAY_ELEVEN);

        assertThrows(
                SkillNotRequiredException.class,
                () -> boardLane.addAssignment(task, startTime, endTime));
    }

    @Test
    void givenTaskAndTime_whenAddAssignment_thenInBoardLane() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime(MONDAY_TEN);
        LocalDateTime endTime = getTime(BoardLaneTest.MONDAY_ELEVEN);
        boardLane.addAssignment(task, startTime, endTime);

        assertTrue(boardLane.numberOfTasks() > 0);
    }

    private LocalDateTime getTime(String dateTime) {
        return LocalDateTime.parse(
                dateTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    void givenTask_whenStartIsOutOfWorkingHours_thenThrowException() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime("2020-01-01 07:00:00");
        LocalDateTime endTime = getTime("2020-01-01 09:00:00");
        assertThrows(
                WorkingHoursException.class,
                () -> boardLane.addAssignment(task, startTime, endTime));
    }

    @Test
    void givenTask_whenEndIsOutOfWorkingHours_thenThrowException() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime("2020-01-01 15:00:00");
        LocalDateTime endTime = getTime("2020-01-01 17:00:00");
        assertThrows(
                WorkingHoursException.class,
                () -> boardLane.addAssignment(task, startTime, endTime));
    }

    @Test
    void givenNewTask_whenStartOverlapsWithExistingTasks_thenThrowException() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime(MONDAY_TEN);
        LocalDateTime endTime = getTime(MONDAY_ELEVEN);
        boardLane.addAssignment(task, startTime, endTime);

        Task newTask = new Task();
        newTask.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime newStartTime = getTime(MONDAY_TEN_THIRTY);
        LocalDateTime newEndTime = getTime(MONDAY_ELEVEN_THIRTY);

        assertThrows(
                TimeCollisionException.class,
                () -> boardLane.addAssignment(newTask, newStartTime, newEndTime));
    }

    @Test
    void givenTwoTasks_whenEndTimeOverlapsWithExistingTasks_thenThrowException() {
        task.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime startTime = getTime(MONDAY_TEN);
        LocalDateTime endTime = getTime(MONDAY_ELEVEN);
        boardLane.addAssignment(task, startTime, endTime);

        Task newTask = new Task();
        newTask.addRequiredSkill(Skill.CONSTRUCTION);
        LocalDateTime newStartTime = getTime(MONDAY_NINE_THIRTY);
        LocalDateTime newEndTime = getTime(MONDAY_TEN_THIRTY);

        assertThrows(
                TimeCollisionException.class,
                () -> boardLane.addAssignment(newTask, newStartTime, newEndTime));
    }
}
