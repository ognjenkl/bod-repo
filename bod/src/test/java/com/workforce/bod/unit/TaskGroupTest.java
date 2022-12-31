package com.workforce.bod.unit;

import com.workforce.bod.ResourceGroup;
import com.workforce.bod.assignment.Resource;
import com.workforce.bod.assignment.Skill;
import com.workforce.bod.assignment.Task;
import com.workforce.bod.assignment.TaskGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

        Map<Skill, Integer> recalculatedSkills = taskGroup.generateNewRecalculationMap(Set.of(task));
        taskGroup.setSkills(recalculatedSkills);

        assertEquals(3, taskGroup.getSkills().get(Skill.CONSTRUCTION));
    }

    @Test
    void givenTaskWithRequiredTwoSkillsQuantityOneAndTwo_whenRecalculateSkills_thenRequiredSkillsNumberGreaterThanZero() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
        task.addRequiredSkill(Skill.DEMOLITION, 2);
        taskGroup.addTask(task);

        Map<Skill, Integer> recalculatedSkills = taskGroup.generateNewRecalculationMap(Set.of(task));
        taskGroup.setSkills(recalculatedSkills);

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
    void givenTaskGroupWithThreeConstructionSkill_whenVerifySkills_thenTrue() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.DEMOLITION);
        taskGroup.addTask(task);

        Map<Skill, Integer> recalculatedSkills = taskGroup.generateNewRecalculationMap(Set.of(task));

        boolean isVerified = taskGroup.verifySkills(recalculatedSkills);

        assertTrue(isVerified);
    }

    @Test
    void givenTaskGroupWithThreeConstructionSkill_whenVerifySkills_thenFalse() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.DEMOLITION);
        taskGroup.addTask(task);
        task.addRequiredSkill(Skill.DEMOLITION);

        Map<Skill, Integer> recalculatedSkills = taskGroup.generateNewRecalculationMap(Set.of(task));

        boolean isVerified = taskGroup.verifySkills(recalculatedSkills);

        assertFalse(isVerified);
    }

    @Test
    void givenResourcesAndTasks_whenHasEnoughEligibleResources_thenTrue() {
        Resource resource1 = createResourceWith(Skill.CONSTRUCTION);
        Resource resource2 = createResourceWith(Skill.DEMOLITION);
        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.addResource(resource1);
        resourceGroup.addResource(resource2);

        Task task = createTaskWithConstructionAndDemolition();
        Map<Skill, Set<Resource>> eligibleResourcesBySkill = new HashMap<>();
        eligibleResourcesBySkill.put(Skill.CONSTRUCTION, Set.of(resource1));
        eligibleResourcesBySkill.put(Skill.DEMOLITION, Set.of(resource2));
        task.setEligibleResources(eligibleResourcesBySkill);

        TaskGroup taskGroup = new TaskGroup();
        taskGroup.addTask(task);

        boolean hasEnoughEligibleResources =
                taskGroup.hasEnoughEligibleResources(resourceGroup);

        assertTrue(hasEnoughEligibleResources);
    }

    private static Resource createResourceWith(Skill construction) {
        Resource resource1 = new Resource();
        resource1.addSkill(construction);
        return resource1;
    }

    private static Task createTaskWithConstructionAndDemolition() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.DEMOLITION);
        return task;
    }

    @Test
    void givenResourcesAndTasks_whenHasEnoughEligibleResources_thenFalse() {
        Resource resource1 = createResourceWith(Skill.CONSTRUCTION);
        Resource resource2 = createResourceWith(Skill.DEMOLITION);
        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.addResource(resource1);
        resourceGroup.addResource(resource2);

        Task task = createTaskWithConstructionAndDemolition();
        Map<Skill, Set<Resource>> eligibleResourcesBySkill = new HashMap<>();
        eligibleResourcesBySkill.put(Skill.CONSTRUCTION, Set.of(resource1));
        task.setEligibleResources(eligibleResourcesBySkill);

        TaskGroup taskGroup = new TaskGroup();
        taskGroup.addTask(task);

        boolean hasEnoughEligibleResources =
                taskGroup.hasEnoughEligibleResources(resourceGroup);

        assertFalse(hasEnoughEligibleResources);
    }
}
