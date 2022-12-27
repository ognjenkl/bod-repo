package com.workforce.bod.unit;

import com.workforce.bod.assignment.Skill;
import com.workforce.bod.assignment.Task;
import com.workforce.bod.assignment.TaskGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskGroupTest {

    private TaskGroup taskGroup;

    @BeforeEach
    void setUp() {
        taskGroup = new TaskGroup();
    }

    @Test
    void givenTaskGrop_whentGetTasks_thenNotNull() {
        assertNotNull(taskGroup.getTasks());
    }

    @Test
    void givenTaskWithRequiredSkillConstruction_whenAddTask_thenRequiredSkillsNumberGreaterThanZero() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        taskGroup.addTask(task);

        assertTrue(taskGroup.getTasks().size() > 0);
        assertTrue(taskGroup.getSkills().size() > 0);
        assertEquals(1, taskGroup.getSkills().get(Skill.CONSTRUCTION));
    }

    @Test
    void givenTaskWithRequiredSkillTwoTimes_whenAddTask_thenRequiredSkillsNumberGreaterThanZero() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION, 2);
        taskGroup.addTask(task);

        assertTrue(taskGroup.getTasks().size() > 0);
        assertTrue(taskGroup.getSkills().size() > 0);
        assertEquals(2, taskGroup.getSkills().get(Skill.CONSTRUCTION));
    }

    @Test
    void givenTaskWithRequiredTwoSkillsQuantityOneAndTwo_whenAddTask_thenRequiredSkillsNumberGreaterThanZero() {
        ;
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
        task.addRequiredSkill(Skill.DEMOLITION, 2);
        taskGroup.addTask(task);

        assertTrue(taskGroup.getTasks().size() > 0);
        assertTrue(taskGroup.getSkills().size() > 0);
        assertEquals(1, taskGroup.getSkills().get(Skill.CONSTRUCTION));
        assertEquals(2, taskGroup.getSkills().get(Skill.DEMOLITION));
    }

    @Test
    void givenTaskGroupWithThreeConstructionSkill_whenRecalculateSkills_thenConstructionIsThree() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.CONSTRUCTION);
        taskGroup.addTask(task);

        taskGroup.recalculateSkills();
        assertEquals(3, taskGroup.getSkills().get(Skill.CONSTRUCTION));
    }

    @Test
    void givenTaskWithRequiredTwoSkillsQuantityOneAndTwo_whenRecalculateSkills_thenRequiredSkillsNumberGreaterThanZero() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
        task.addRequiredSkill(Skill.DEMOLITION, 2);
        taskGroup.addTask(task);

        taskGroup.recalculateSkills();

        assertTrue(taskGroup.getTasks().size() > 0);
        assertTrue(taskGroup.getSkills().size() > 0);
        assertEquals(1, taskGroup.getSkills().get(Skill.CONSTRUCTION));
        assertEquals(2, taskGroup.getSkills().get(Skill.DEMOLITION));
    }

    @Test
    void givenTask_whenAddAndRemoveTask_thenTasksZeroAndRequiredSkillsNumberZero() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        taskGroup.addTask(task);

        taskGroup.removeTask(task);

        assertTrue(taskGroup.getTasks().size() == 0);
        assertTrue(taskGroup.getSkills().size() == 0);
    }

    @Test
    void givenTask_whenAddAndTwoRemoveTask_thenThrowIllegalStateException() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        taskGroup.addTask(task);

        taskGroup.removeTask(task);
        assertThrows(IllegalStateException.class, () -> taskGroup.removeTask(task));
    }

    @Test
    void givenTaskGroupWithThreeConstructionSkill_whenVerifySkills_theTrue() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.DEMOLITION);
        taskGroup.addTask(task);

        boolean isVerified = taskGroup.verifySkills(taskGroup);

        assertTrue(isVerified);
    }
}
