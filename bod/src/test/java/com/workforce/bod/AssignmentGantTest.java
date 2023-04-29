package com.workforce.bod;

import com.workforce.bod.assignment.AssignmentGant;
import com.workforce.bod.assignment.Resource;
import com.workforce.bod.assignment.Skill;
import com.workforce.bod.assignment.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssignmentGantTest {

    AssignmentGant gant;
    Resource resourceConstruction;
    Task task;

    @BeforeEach
    void setUp() {
        resourceConstruction = new Resource(Skill.CONSTRUCTION);
        task = new Task();
        gant = new AssignmentGant(resourceConstruction);
    }

    @Test
    void givenEligibleTask_whenAddTask_thenInGant() {
        task.addRequiredSkill(Skill.CONSTRUCTION);

        gant.addTask(task);

        assertTrue(gant.numberOfTasks() > 0);
    }

    @Test
    void givenTaskWithNoRequiredSkills_whenAddTask_thenInGant() {
        gant.addTask(task);

        assertTrue(gant.numberOfTasks() > 0);
    }

    @Test
    void givenNotEligibleTask_whenAddTask_thenThrowNotEligibleTaskException() {
        task.addRequiredSkill(Skill.DEMOLITION);
        assertThrows(
                AssignmentGant.NotEligibleTaskException.class,
                () -> gant.addTask(task));
        // if cant add task to gant, then throw exception
    }
}
