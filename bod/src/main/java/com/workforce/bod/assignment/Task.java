package com.workforce.bod.assignment;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Task {

    private final List<Skill> requiredSkills = new ArrayList<>();

    public void addRequiredSkill(Skill skill) {
        requiredSkills.add(skill);
    }

//    public Integer addRequiredSkill(Skill skill, Integer numberOfSkills) {
//
//        if (requiredSkills.containsKey(skill)) {
//            numberOfSkills += requiredSkills.get(skill);
//        }
//
//        requiredSkills.put(skill, numberOfSkills);
//        return numberOfSkills;
//    }

//    public void addRequiredSkill(Skill skill) {
//        requiredSkills.add(skill);
//    }
//
//    public Integer getRequiredSkillsNumber(Skill skill) {
//        Integer numOfSkills = requiredSkills.get(skill);
//        return numOfSkills != null ? numOfSkills : 0;
//    }
}
