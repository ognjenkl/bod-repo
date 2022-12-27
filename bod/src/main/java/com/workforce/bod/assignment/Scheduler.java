package com.workforce.bod.assignment;

import java.util.List;
import java.util.Set;

public interface Scheduler {

    Set<Assignment> schedule(List<Task> tasks, List<Resource> resources);

    Boolean resourceHasRequiredSillsNumber(List<Task> tasks, List<Resource> resources);
}
