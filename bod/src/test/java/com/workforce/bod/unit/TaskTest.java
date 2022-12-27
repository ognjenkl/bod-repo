package com.workforce.bod.unit;

import com.workforce.bod.assignment.Skill;
import com.workforce.bod.assignment.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
    }

    @Test
    void givenRequiredSkill_whenAddRequiredSkillToTask_thenReturnNumberOfRequiredSkills() {
        Integer numberOfSkills = task.addRequiredSkill(Skill.CONSTRUCTION, 1);

        assertEquals(1, numberOfSkills);
    }

    @Test
    void givenRequiredSkills_whenAddRequiredSkillsToTask_thenReturnNumberOfRequiredSkills() {
        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
        task.addRequiredSkill(Skill.CONSTRUCTION, 3);
        Integer numberOfSkills = task.addRequiredSkill(Skill.CONSTRUCTION, 1);

        assertEquals(5, numberOfSkills);
    }

    @Test
    void givenRequiredSkillWithoutNumber_whenAddRequiredSkillToTask_thenReturnOneRequiredSkill() {
        Integer numberOfSkills = task.addRequiredSkill(Skill.CONSTRUCTION);

        assertEquals(1, numberOfSkills);
    }

    @Test
    void givenSameSkills_whenGetRequiredSkillsNumber_thenReturnNumberOfRequiredSkills() {
        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
        task.addRequiredSkill(Skill.CONSTRUCTION, 3);
        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
        Integer numberOfSkills = task.getRequiredSkillsNumber(Skill.CONSTRUCTION);

        assertEquals(5, numberOfSkills);
    }

    @Test
    void givenDifferentSkills_whenGetRequiredSkillsNumber_thenReturnNumberOfRequiredSkills() {
        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
        task.addRequiredSkill(Skill.DEMOLITION, 3);
        task.addRequiredSkill(Skill.CONSTRUCTION, 1);
        Integer numberOfSkills = task.getRequiredSkillsNumber(Skill.CONSTRUCTION);

        assertEquals(2, numberOfSkills);
    }

    @Test
    void givenNoSkills_whenGetRequiredSkillsNumber_thenReturnZeroRequiredSkills() {
        Integer numberOfSkills = task.getRequiredSkillsNumber(Skill.CONSTRUCTION);

        assertEquals(0, numberOfSkills);
    }

}