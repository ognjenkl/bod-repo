package com.workforce.bod.assignment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class Resource {

    private final Skill skill;
    private final LocalDateTime workStart;
    private final LocalDateTime workEnd;

//    private final Set<Skill> skills = new HashSet<>();

//    public boolean contains(Skill skill) {
//        return skills.contains(skill);
//    }

//    public void addSkill(Skill skill) {
//        skills.add(skill);
//    }
}
