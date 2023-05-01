package com.workforce.bod.assignment.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {

    Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
    }

//    @Test
//    void givenRequiredSkill_whenAddRequiredSkillToTask_thenReturnNumberOfRequiredSkills() {
//        Integer numberOfSkills = task.addRequiredSkill(Skill.CONSTRUCTION, 1);
//
//        assertEquals(1, numberOfSkills);
//    }
//
//    @Test
//    void givenRequiredSkills_whenAddRequiredSkillsToTask_thenReturnNumberOfRequiredSkills() {
//        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
//        task.addRequiredSkill(Skill.CONSTRUCTION, 3);
//        Integer numberOfSkills = task.addRequiredSkill(Skill.CONSTRUCTION, 1);
//
//        assertEquals(5, numberOfSkills);
//    }
//
//    @Test
//    void givenRequiredSkillWithoutNumber_whenAddRequiredSkillToTask_thenReturnOneRequiredSkill() {
//        Integer numberOfSkills = task.addRequiredSkill(Skill.CONSTRUCTION);
//
//        assertEquals(1, numberOfSkills);
//    }
//
//    @Test
//    void givenSameSkills_whenGetRequiredSkillsNumber_thenReturnNumberOfRequiredSkills() {
//        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
//        task.addRequiredSkill(Skill.CONSTRUCTION, 3);
//        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
//        Integer numberOfSkills = task.getRequiredSkillsNumber(Skill.CONSTRUCTION);
//
//        assertEquals(5, numberOfSkills);
//    }
//
//    @Test
//    void givenDifferentSkills_whenGetRequiredSkillsNumber_thenReturnNumberOfRequiredSkills() {
//        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
//        task.addRequiredSkill(Skill.DEMOLITION, 3);
//        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
//        Integer numberOfSkills = task.getRequiredSkillsNumber(Skill.CONSTRUCTION);
//
//        assertEquals(2, numberOfSkills);
//    }
//
//    @Test
//    void givenNoSkills_whenGetRequiredSkillsNumber_thenReturnZeroRequiredSkills() {
//        Integer numberOfSkills = task.getRequiredSkillsNumber(Skill.CONSTRUCTION);
//
//        assertEquals(0, numberOfSkills);
//    }
//
//    @Test
//    void givenTaskAndResources_whenHasEnoughEligibleResources_thenTrue() {
//        Resource resource1 = new Resource();
//        resource1.addSkill(Skill.CONSTRUCTION);
//        Resource resource2= new Resource();
//        resource2.addSkill(Skill.DEMOLITION);
//        ResourceGroup resourceGroup = new ResourceGroup();
//        resourceGroup.addResource(resource1);
//        resourceGroup.addResource(resource2);
//
//        Task task = new Task();
//        task.addRequiredSkill(Skill.CONSTRUCTION);
//        task.addRequiredSkill(Skill.DEMOLITION);
//
//        Map<Skill, Set<Resource>> eligibleResourcesBySkill = new HashMap<>();
//        eligibleResourcesBySkill.put(Skill.CONSTRUCTION, Set.of(resource1));
//        eligibleResourcesBySkill.put(Skill.DEMOLITION, Set.of(resource2));
//        task.setEligibleResources(eligibleResourcesBySkill);
//
//        boolean hasEnoughEligibleResources =
//                task.hasEnoughEligibleResources();
//
//        assertTrue(hasEnoughEligibleResources);
//    }
//
//    @Test
//    void givenTaskAndResources_whenHasEnoughEligibleResources_thenFalse() {
//        Resource resource1 = new Resource();
//        resource1.addSkill(Skill.CONSTRUCTION);
//        Resource resource2= new Resource();
//        resource2.addSkill(Skill.DEMOLITION);
//        ResourceGroup resourceGroup = new ResourceGroup();
//        resourceGroup.addResource(resource1);
//        resourceGroup.addResource(resource2);
//
//        Task task = new Task();
//        task.addRequiredSkill(Skill.CONSTRUCTION);
//        task.addRequiredSkill(Skill.DEMOLITION);
//
//        Map<Skill, Set<Resource>> eligibleResourcesBySkill = new HashMap<>();
//        eligibleResourcesBySkill.put(Skill.CONSTRUCTION, Set.of(resource1));
//        task.setEligibleResources(eligibleResourcesBySkill);
//
//        boolean hasEnoughEligibleResources =
//                task.hasEnoughEligibleResources();
//
//        assertFalse(hasEnoughEligibleResources);
//    }

    @Test
    void givenSkill_whenAddRequiredSkillToTask_thenSillInTask() {
        task.addRequiredSkill(Skill.CONSTRUCTION);

        assertTrue(task.getRequiredSkills().size() > 0);
    }

    @Test
    void givenTaskWithNoSkills_whenGetRequiredSkills_thenReturnEmptyList() {
        assertTrue(task.getRequiredSkills().isEmpty());
    }

}