package com.workforce.bod.assignment.board;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WeekAvailabilityClockImpl implements WeekAvailabilityClock {

    @Override
    public LocalDateTime getLocalDateTime(DayOfWeek dayOfWeek, LocalTime localTime) {
        if (dayOfWeek == null || localTime == null)
            throw new IllegalArgumentException();
        return createLocalDateTime().withDayOfMonth(dayOfWeek.getValue()).with(localTime);
    }

    LocalDateTime createLocalDateTime() {
        return LocalDateTime.now();
    }

    @Override
    public List<WeekDayInterval> getWeekDayIntervals(
            DayOfWeek startDay, LocalTime startTime,
            DayOfWeek endDay, LocalTime endTime) {

        if (startDay == null || startTime == null
                || endDay == null || endTime == null) {
            throw new IllegalArgumentException();
        }

        List<LocalDateTime> localDateTimes = getLocalDateTimes(
                startDay, startTime, endDay, endTime);

        LocalDateTime newStart;
        List<WeekDayInterval> weekDayIntervals = new ArrayList<>();
        LocalDateTime midnight = localDateTimes.get(0);


        while (midnight.getDayOfMonth() != localDateTimes.get(1).getDayOfMonth()) {
            newStart = midnight;
            midnight = midnight.plusDays(1).with(LocalTime.of(0, 0));
            weekDayIntervals.add(new WeekDayInterval(
                    newStart.getDayOfWeek(),
                    newStart.toLocalTime(),
                    midnight.toLocalTime()));
        }

        if (midnight.getDayOfMonth() == localDateTimes.get(1).getDayOfMonth()) {
            weekDayIntervals.add(new WeekDayInterval(
                    midnight.getDayOfWeek(),
                    midnight.toLocalTime(),
                    localDateTimes.get(1).toLocalTime()));
        }

        return weekDayIntervals;
    }

    @Override
    public List<LocalDateTime> getLocalDateTimes(
            DayOfWeek startDay, LocalTime startTime,
            DayOfWeek endDay, LocalTime endTime) {
        LocalDateTime start = getLocalDateTime(startDay, startTime);
        LocalDateTime end = getLocalDateTime(endDay, endTime);
        if (start.isAfter(end)) {
            end = getLocalDateTime(endDay, endTime).plusDays(7);
        }

        List<LocalDateTime> localDateTimes = new ArrayList<>();
        localDateTimes.add(start);
        localDateTimes.add(end);
        return localDateTimes;
    }
}
