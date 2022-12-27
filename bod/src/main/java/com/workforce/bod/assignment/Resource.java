package com.workforce.bod.assignment;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Resource {

    private final List<Skill> skills = new ArrayList<>();

    public boolean contains(Skill skill) {
        return skills.contains(skill);
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }
}
