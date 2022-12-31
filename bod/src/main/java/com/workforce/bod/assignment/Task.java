package com.workforce.bod.assignment;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
public class Task {

    private final Map<Skill, Integer> requiredSkills = new HashMap<>();
    private Map<Skill, Set<Resource>> eligibleResources;

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

    public void setEligibleResources(Map<Skill, Set<Resource>> eligibleResources) {
        this.eligibleResources = eligibleResources;
    }

    public boolean hasEnoughEligibleResources() {
        return hasEnoughEligibleResourcesByRquiredSkills();
    }

    private boolean hasEnoughEligibleResourcesByRquiredSkills() {
        for (Map.Entry<Skill, Integer> entry : requiredSkills.entrySet()) {
            if (eligibleResources.get(entry.getKey()) == null
                    || entry.getValue() > eligibleResources.get(entry.getKey()).size()) {
                return false;
            }
        }
        return true;
    }
}
