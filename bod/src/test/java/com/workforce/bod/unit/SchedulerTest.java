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
        Resource resource = new Resource();
        resource.addSkill(Skill.CONSTRUCTION);

        Set<Assignment> assignments = scheduler.schedule(
                List.of(task),
                List.of(resource));

        assertEquals(1, assignments.size());
    }

    @Test
    void givenOneTaskAndOneResourceWithMatchingSkills_whenSchedule_thenReturnOneAssignment() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.DEMOLITION);

        Resource resource = new Resource();
        resource.addSkill(Skill.CONSTRUCTION);
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

        Resource resource = new Resource();
        resource.addSkill(Skill.CONSTRUCTION);
        List<Resource> resources = List.of(resource);
        Set<Assignment> assignments = scheduler.schedule(tasks, resources);

        assertEquals(1, assignments.size());
    }

    @Test
    void givenOneTaskAndOneResourceWithNotMatchingSkill_whenSchedule_thenReturnResourceSkillNumberMatchZero() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        List<Task> tasks = List.of(task);

        Resource resource = new Resource();
        resource.addSkill(Skill.DEMOLITION);
        List<Resource> resources = List.of(resource);

        Set<Assignment> assignments = scheduler.schedule(tasks, resources);

        assertEquals(0, assignments.size());
    }

    @Test
    void givenOneTaskTwoSkillsAndOneResourceWithTwoSkills_whenResourceHasRequiredSkillsNumber_thenTrue() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.DEMOLITION);
        List<Task> tasks = List.of(task);

        Resource resource = new Resource();
        resource.addSkill(Skill.CONSTRUCTION);
        resource.addSkill(Skill.DEMOLITION);
        List<Resource> resources = List.of(resource);

        boolean hasRequiredSills = scheduler.resourceHasRequiredSillsNumber(tasks, resources);

        assertTrue(hasRequiredSills);
    }

    @Test
    void givenOneTaskTwoSkillsAndTwoResourcesByOneDiffSkill_whenResourceHasRequiredSkillsNumber_thenTrue() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.DEMOLITION);
        List<Task> tasks = List.of(task);

        Resource resource1 = new Resource();
        resource1.addSkill(Skill.CONSTRUCTION);
        Resource resource2= new Resource();
        resource2.addSkill(Skill.DEMOLITION);
        List<Resource> resources = List.of(resource1, resource2);

        boolean hasRequiredSills = scheduler.resourceHasRequiredSillsNumber(tasks, resources);

        assertTrue(hasRequiredSills);
    }

    @Test
    void givenOneTaskTwoSkillsAndTwoResourcesByOneSameSkill_whenResourceHasRequiredSkillsNumber_thenFalse() {
        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.DEMOLITION);
        List<Task> tasks = List.of(task);

        Resource resource1 = new Resource();
        resource1.addSkill(Skill.CONSTRUCTION);
        Resource resource2= new Resource();
        resource2.addSkill(Skill.CONSTRUCTION);
        List<Resource> resources = List.of(resource1, resource2);

        boolean hasRequiredSills = scheduler.resourceHasRequiredSillsNumber(tasks, resources);

        assertFalse(hasRequiredSills);
    }

    @Test
    void givenTaskAndResource_whenGetEligibleResourcesByMatchingSkillsNumber_thenNotNull() {
        Map<Task, Map<Skill, Set<Resource>>> eligibleTaskResources =
                scheduler.getEligibleTaskResources(null, null);

        assertNotNull(eligibleTaskResources);
    }

    @Test
    void givenTaskAndResource_whenGetEligibleResourcesByMatchingSkillsNumber_thenReturnNotNull() {
        Resource resource1 = new Resource();
        resource1.addSkill(Skill.CONSTRUCTION);
        Resource resource2= new Resource();
        resource2.addSkill(Skill.DEMOLITION);
        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.addResource(resource1);
        resourceGroup.addResource(resource2);

        Task task = new Task();
        task.addRequiredSkill(Skill.CONSTRUCTION);
        task.addRequiredSkill(Skill.DEMOLITION);
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.addTask(task);

        Map<Task, Map<Skill, Set<Resource>>> eligibleTaskResources =
                scheduler.getEligibleTaskResources(taskGroup, resourceGroup);

        assertNotNull(eligibleTaskResources);
    }

    // TODO 2022-12-25 test resource with skills number for required tasks
}
