package com.workforce.bod.assignment;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SchedulerImpl implements Scheduler {

    @Override
    public Set<Assignment> schedule(List<Task> tasks, List<Resource> resources) {
        Set<Assignment> assignments = new HashSet<>();

        if (tasks == null || resources == null) {
            return assignments;
        }

        for (Task task : tasks) {
            for (Resource resource : resources) {
                if (isSkillMatch(task.getRequiredSkills(), resource.getSkills())) {
                    assignments.add(new Assignment(task, resource));
                }
            }
        }

        return assignments;
    }

    @Override
    public Boolean resourceHasRequiredSillsNumber(List<Task> tasks, List<Resource> resources) {
        for (Task task : tasks) {
            Map<Skill, Integer> requiredSkills = task.getRequiredSkills();
            for (Skill skill : requiredSkills.keySet()) {
                Integer resourceSkillNumber = getResourceSkillNumber(resources, skill);
                if (task.getRequiredSkillsNumber(skill) > (resourceSkillNumber)) {
                    return false;
                }
            }
        }
        return true;
    }

    private Integer getResourceSkillNumber(List<Resource> resources, Skill skill) {
        return resources.stream()
                .mapToInt(resource -> resource.getSkills().contains(skill) ? 1 : 0)
                .sum();
    }

    private Boolean isSkillMatch(Map<Skill, Integer> taskRequiredSkills, List<Skill> resourceSkills) {
        return resourceSkills.containsAll(taskRequiredSkills.keySet());
    }
}
