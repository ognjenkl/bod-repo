package com.workforce.bod;

import com.workforce.bod.assignment.Resource;
import com.workforce.bod.assignment.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceTest {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String SUNDAY = "2023-04-30 12:00:00";
    public static final String SATURDAY = "2023-04-29 12:00:00";
    public static final String FRIDAY = "2023-04-28 12:00:00";
    public static final String THURSDAY = "2023-04-27 12:00:00";
    public static final String WEDNESDAY = "2023-04-26 12:00:00";
    public static final String TUESDAY = "2023-04-25 12:00:00";
    public static final String MONDAY = "2023-04-24 12:00:00";
    Resource resource;

    @BeforeEach
    void setUp() {
        AvailabilityCalendar availabilityCalendar = new AvailabilityCalendar();
        WeekAvailability weekAvailability = createWeekAvailabilityFileWorkingDays();
        availabilityCalendar.addWeekAvailability(weekAvailability);

        resource = new Resource(Skill.CONSTRUCTION, availabilityCalendar);
    }

    private static WeekAvailability createWeekAvailabilityFileWorkingDays() {
        WeekAvailability weekAvailability = new WeekAvailability();

        WeekAvailabilityInterval weekAvailabilityInterval = createAvailabilityInterval(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                AvailableToWork.WORK);
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        weekAvailabilityInterval = createAvailabilityInterval(
                DayOfWeek.TUESDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                AvailableToWork.WORK);
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        weekAvailabilityInterval = createAvailabilityInterval(
                DayOfWeek.WEDNESDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                AvailableToWork.WORK);
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        weekAvailabilityInterval = createAvailabilityInterval(
                DayOfWeek.THURSDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                AvailableToWork.WORK);
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        weekAvailabilityInterval = createAvailabilityInterval(
                DayOfWeek.FRIDAY,
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                AvailableToWork.WORK);
        weekAvailability.addWorkInterval(weekAvailabilityInterval);

        return weekAvailability;
    }

    private static WeekAvailabilityInterval createAvailabilityInterval(DayOfWeek dayOfWeek,
                                                                       LocalTime start,
                                                                       LocalTime end,
                                                                       AvailableToWork availableToWork) {
        WeekDayInterval weekDayInterval = new WeekDayInterval(dayOfWeek, start, end);
        return new WeekAvailabilityInterval(availableToWork, weekDayInterval);
    }

    @Test
    void givenResourceWitOutWeekAvailability_whenIsInWorkTime_thenFalse() {
        assertFalse(resource.isInWorkTime(null));
    }

    @Test
    void givenMultipleDays_whenCheckIsAllProperlyManagedAsWorkingDay_thenTrue() {
        boolean isInWorkingTime = resource.isInWorkTime(getTime(MONDAY))
                && resource.isInWorkTime(getTime(TUESDAY))
                && resource.isInWorkTime(getTime(WEDNESDAY))
                && resource.isInWorkTime(getTime(THURSDAY))
                && resource.isInWorkTime(getTime(FRIDAY))
                && !resource.isInWorkTime(getTime(SATURDAY))
                && !resource.isInWorkTime(getTime(SUNDAY));

        assertTrue(isInWorkingTime);
    }

    private static LocalDateTime getTime(String time) {
        return LocalDateTime.parse(time, FORMATTER);
    }
}