package com.workforce.bod;

import com.workforce.bod.assignment.Assignment;
import com.workforce.bod.exception.TimeOrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class AssignmentTest {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String START = "2020-01-01 10:00:00";
    public static final String END = "2020-01-01 11:00:00";

    Assignment assignment;
    LocalDateTime startTime;
    LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.parse(START, FORMATTER);
        endTime = LocalDateTime.parse(END, FORMATTER);
    }

    @Test
    void givenAssignment_whenStartIsAfterEndTime_throwException() {
        assertThrows(TimeOrderException.class, () -> new Assignment(null, null, endTime, startTime));
    }

    @Test
    void givenTime_whenIsInInterval_theTrue() {
        assignment = new Assignment(null, null, startTime, endTime);

        String timeToCheck = "2020-01-01 10:30:00";
        boolean isInInterval = assignment.isInTimeInterval(LocalDateTime.parse(timeToCheck, FORMATTER));

        assertTrue(isInInterval);

    }

    @Test
    void givenTime_whenIsInInterval_theFalse() {
        assignment = new Assignment(null, null, startTime, endTime);

        String timeToCheck = "2020-01-01 09:30:00";
        boolean isInInterval = assignment.isInTimeInterval(LocalDateTime.parse(timeToCheck, FORMATTER));

        assertFalse(isInInterval);
    }

    @Test
    void givenAssignment_whenStartIsInTimeCollision_theTrue() {
        assignment = new Assignment(null, null, startTime, endTime);
        String startToCheck = "2020-01-01 10:30:00";
        String endToCheck = "2020-01-01 11:30:00";
        LocalDateTime start = LocalDateTime.parse(startToCheck, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endToCheck, FORMATTER);
        Assignment assignmentToCheck = new Assignment(null, null, start, end);

        boolean isInInterval = assignment.isInTimeCollision(assignmentToCheck);

        assertTrue(isInInterval);
    }

    @Test
    void givenAssignment_whenEndIsInTimeCollision_thenTrue() {
        assignment = new Assignment(null, null, startTime, endTime);
        String startToCheck = "2020-01-01 09:30:00";
        String endToCheck = "2020-01-01 10:30:00";
        LocalDateTime start = LocalDateTime.parse(startToCheck, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endToCheck, FORMATTER);
        Assignment assignmentToCheck = new Assignment(null, null, start, end);

        boolean isInInterval = assignment.isInTimeCollision(assignmentToCheck);

        assertTrue(isInInterval);
    }

    @Test
    void givenAssignment_whenIsInTimeCollision_thenFalse() {
        // test
        assignment = new Assignment(null, null, startTime, endTime);
        String startToCheck = "2020-01-01 08:00:00";
        String endToCheck = "2020-01-01 09:00:00";
        LocalDateTime start = LocalDateTime.parse(startToCheck, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endToCheck, FORMATTER);
        Assignment assignmentToCheck = new Assignment(null, null, start, end);

        boolean isInInterval = assignment.isInTimeCollision(assignmentToCheck);

        assertFalse(isInInterval);
    }
}
