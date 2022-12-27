package com.workforce.bod.assignment;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Task {

    private final Map<Skill, Integer> requiredSkills = new HashMap<>();

    public Integer addRequiredSkill(Skill skill, Integer numberOfSkills) {

        if (requiredSkills.containsKey(skill)) {
            numberOfSkills += requiredSkills.get(skill);
        }

        requiredSkills.put(skill, numberOfSkills);
        return numberOfSkills;
    }

    public Integer addRequiredSkill(Skill skill) {
        return addRequiredSkill(skill, 1);
    }

    public Integer getRequiredSkillsNumber(Skill skill) {
        Integer numOfSkills = requiredSkills.get(skill);
        return numOfSkills != null ? numOfSkills : 0;
    }
}
