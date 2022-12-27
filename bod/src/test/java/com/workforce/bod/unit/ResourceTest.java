package com.workforce.bod.unit;

import com.workforce.bod.assignment.Resource;
import com.workforce.bod.assignment.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceTest {

    Resource resource;

    @BeforeEach
    void setUp() {
        resource = new Resource();
        resource.addSkill(Skill.CONSTRUCTION);
    }

    @Test
    void givenSkill_whenResourceContainsSkill_thenReturnTrue() {
        boolean containsSkill = resource.contains(Skill.CONSTRUCTION);

        assertTrue(containsSkill);
    }

    @Test
    void givenSkill_whenResourceContainsSkill_thenReturnFalse() {
        boolean containsSkill = resource.contains(Skill.DEMOLITION);

        assertFalse(containsSkill);
    }
}