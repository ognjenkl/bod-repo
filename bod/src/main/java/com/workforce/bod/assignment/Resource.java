package com.workforce.bod.assignment;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Resource {

    private final Set<Skill> skills = new HashSet<>();

    public boolean contains(Skill skill) {
        return skills.contains(skill);
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }
}
