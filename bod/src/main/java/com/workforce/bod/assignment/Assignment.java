package com.workforce.bod.assignment;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class Assignment {

    private final Task task;
    private final Resource resource;
    private final LocalDateTime start;
    private final LocalDateTime end;
}
