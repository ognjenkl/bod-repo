package com.workforce.bod.unit;

import com.workforce.bod.ResourceGroup;
import com.workforce.bod.assignment.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SchedulerTest {

    SchedulerImpl scheduler;

    @BeforeEach
    void setUp() {
        scheduler = new SchedulerImpl();
    }

    @Test
    void givenNullTasksAndResources_whenScheduling_thenReturnNotNull() {
        Set<Assignment> assignments = scheduler.schedule(null, null);

        assertNotNull(assignments);
    }

    @Test
    void givenEmptyTasksAndResources_whenScheduling_thenReturnNotNull() {
        Set<Assignment> assignments = scheduler.schedule(List.of(), List.of());

        assertNotNull(assignments);
    }

    @Test
    void givenNullTasksAndEmptyResources_whenScheduling_thenReturnNotNull() {
        Set<Assignment> assignments = scheduler.schedule(null, List.of());

        assertNotNull(assignments);
    }

    @Test
    void givenEmptyTasksAndNullResources_whenScheduling_thenReturnNotNull() {
        Set<Assignment> assignments = scheduler.schedule(List.of(), null);

        assertNotNull(assignments);
    }


    @Test
    void givenOneTaskAndOneResourceWithMatchingSkill_whenSchedule_thenReturnOneAssignment() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        Resource resource = createResourceWith(Skill.CONSTRUCTION);

        Set<Assignment> assignments = scheduler.schedule(
                List.of(task),
                List.of(resource));

        assertEquals(1, assignments.size());
    }

    @Test
    void givenOneTaskAndOneResourceWithMatchingSkills_whenSchedule_thenReturnOneAssignment() {
        Task task = createTaskWithConstructionAndDemolition();

        Resource resource = createResourceWith(Skill.CONSTRUCTION);
        resource.addSkill(Skill.DEMOLITION);

        Set<Assignment> assignments = scheduler.schedule(
                List.of(task),
                List.of(resource));

        assertEquals(1, assignments.size());
    }

    @Test
    void givenOneTaskAndOneResourceWithMatchingSkill_whenSchedule_thenReturnResourceSkillNumberMatchOne() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        List<Task> tasks = List.of(task);

        Resource resource = createResourceWith(Skill.CONSTRUCTION);
        List<Resource> resources = List.of(resource);
        Set<Assignment> assignments = scheduler.schedule(tasks, resources);

        assertEquals(1, assignments.size());
    }

    @Test
    void givenOneTaskAndOneResourceWithNotMatchingSkill_whenSchedule_thenReturnResourceSkillNumberMatchZero() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        List<Task> tasks = List.of(task);

        Resource resource = createResourceWith(Skill.DEMOLITION);
        List<Resource> resources = List.of(resource);

        Set<Assignment> assignments = scheduler.schedule(tasks, resources);

        assertEquals(0, assignments.size());
    }

    @Test
    void givenOneTaskTwoSkillsAndOneResourceWithTwoSkills_whenResourceHasRequiredSkillsNumber_thenTrue() {
        Task task = createTaskWithConstructionAndDemolition();
        List<Task> tasks = List.of(task);

        Resource resource = createResourceWith(Skill.CONSTRUCTION);
        resource.addSkill(Skill.DEMOLITION);
        List<Resource> resources = List.of(resource);

        boolean hasRequiredSills = scheduler.resourceHasRequiredSillsNumber(tasks, resources);

        assertTrue(hasRequiredSills);
    }

    @Test
    void givenOneTaskTwoSkillsAndTwoResourcesByOneDiffSkill_whenResourceHasRequiredSkillsNumber_thenTrue() {
        Task task = createTaskWithConstructionAndDemolition();
        List<Task> tasks = List.of(task);

        Resource resource1 = createResourceWith(Skill.CONSTRUCTION);
        Resource resource2 = createResourceWith(Skill.DEMOLITION);
        List<Resource> resources = List.of(resource1, resource2);

        boolean hasRequiredSills = scheduler.resourceHasRequiredSillsNumber(tasks, resources);

        assertTrue(hasRequiredSills);
    }

    @Test
    void givenOneTaskTwoSkillsAndTwoResourcesByOneSameSkill_whenResourceHasRequiredSkillsNumber_thenFalse() {
        Task task = createTaskWithConstructionAndDemolition();
        List<Task> tasks = List.of(task);

        Resource resource1 = createResourceWith(Skill.CONSTRUCTION);
        Resource resource2 = createResourceWith(Skill.CONSTRUCTION);
        List<Resource> resources = List.of(resource1, resource2);

        boolean hasRequiredSills = scheduler.resourceHasRequiredSillsNumber(tasks, resources);

        assertFalse(hasRequiredSills);
    }

    @Test
    void givenTaskAndResource_whenGetEligibleResourcesByMatchingSkillsNumber_thenNotNull() {
        Map<Task, Map<Skill, Set<Resource>>> eligibleTaskResources =
                scheduler.assignEligibleTaskResources(null, null);

        assertNotNull(eligibleTaskResources);
    }

    @Test
    void givenTaskAndResource_whenGetEligibleResourcesByMatchingSkillsNumber_thenReturnNotNull() {
        Task task = createTaskWithConstructionAndDemolition();
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.addTask(task);

        Resource resource1 = createResourceWith(Skill.CONSTRUCTION);
        Resource resource2 = createResourceWith(Skill.DEMOLITION);
        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.addResource(resource1);
        resourceGroup.addResource(resource2);

        Map<Task, Map<Skill, Set<Resource>>> eligibleTaskResources =
                scheduler.assignEligibleTaskResources(taskGroup, resourceGroup);

        assertNotNull(eligibleTaskResources);
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
    void givenTasksAndEnoughResources_whenHasEnoughSkilledResources_thenTrue() {
        Task task = createTaskWithConstructionAndDemolition();
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.addTask(task);

        Resource resource1 = createResourceWith(Skill.CONSTRUCTION);
        Resource resource2 = createResourceWith(Skill.DEMOLITION);
        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.addResource(resource1);
        resourceGroup.addResource(resource2);

        scheduler.assignEligibleTaskResources(taskGroup, resourceGroup);

        boolean hasEnoughSkilledResources = scheduler.hasEnoughSkilledResourcesForTasks(taskGroup, resourceGroup);

        assertTrue(hasEnoughSkilledResources);
    }

    @Test
    void givenTasksAndNotEnoughResources_whenHasEnoughSkilledResources_thenFalse() {
        Task task = createTaskWithConstructionAndDemolition();
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.addTask(task);

        Resource resource1 = createResourceWith(Skill.CONSTRUCTION);
        Resource resource2 = createResourceWith(Skill.CONSTRUCTION);
        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.addResource(resource1);
        resourceGroup.addResource(resource2);

        scheduler.assignEligibleTaskResources(taskGroup, resourceGroup);

        boolean hasEnoughSkilledResources =
                scheduler.hasEnoughSkilledResourcesForTasks(
                        taskGroup, resourceGroup);

        assertFalse(hasEnoughSkilledResources);
    }

    @Test
    void givenTasksAndToManyResources_whenHasEnoughSkilledResources_thenFalse() {
        Task task = createTaskWithConstructionAndDemolition();
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.addTask(task);

        Resource resource1 = createResourceWith(Skill.CONSTRUCTION);
        Resource resource2 = createResourceWith(Skill.CONSTRUCTION);
        Resource resource3 = createResourceWith(Skill.DEMOLITION);
        Resource resource4 = createResourceWith(Skill.DEMOLITION);
        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.addResource(resource1);
        resourceGroup.addResource(resource2);
        resourceGroup.addResource(resource3);
        resourceGroup.addResource(resource4);

        scheduler.assignEligibleTaskResources(taskGroup, resourceGroup);

        boolean hasEnoughSkilledResources =
                scheduler.hasEnoughSkilledResourcesForTasks(
                        taskGroup, resourceGroup);

        assertTrue(hasEnoughSkilledResources);
    }
}
