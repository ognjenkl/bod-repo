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
}
