package com.workforce.bod.assignment.board;

public interface TaskStatusRepository {
    TaskStatus getDefaultStatus();

    TaskStatus getByStatus(String status);
}
