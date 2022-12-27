package com.workforce.bod;

import com.workforce.bod.assignment.Resource;
import com.workforce.bod.assignment.Skill;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResourceGroup {

    private Set<Resource> resources = new HashSet<>();
    private Set<Skill> skills = new HashSet<>();

    public void addResource(Resource resource) {
        resources.add(resource);
        skills.addAll(resource.getSkills());
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public Set<Skill> getSkills() {
        return skills;
    }
}
