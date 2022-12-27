package com.workforce.bod.assignment;

import com.workforce.bod.ResourceGroup;

import java.util.*;

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

    // TODO: 12/27/2022 optimize
    @Override
    public Map<Task, Map<Skill, Resource>> getEligibleTaskResources(TaskGroup tasks, ResourceGroup resourceGroup) {
        Map<Task, Map<Skill, Resource>> eligibleTaskResources = new HashMap<>();
        if (tasks == null || resourceGroup == null) {
            return eligibleTaskResources;
        }

        for (Task task : tasks.getTasks()) {
            Map<Skill, Resource> eligibleResources = getEligibleResources(task, resourceGroup);
            if (!eligibleResources.isEmpty()) {
                eligibleTaskResources.put(task, eligibleResources);
            }
        }
        return eligibleTaskResources;
    }

    private Map<Skill, Resource> getEligibleResources(Task task, ResourceGroup resourceGroup) {
        Map<Skill, Resource> eligibleResources = new HashMap<>();
        for (Resource resource : resourceGroup.getResources()) {
            for (Skill skill : resource.getSkills()) {
                if (task.getRequiredSkills().keySet().contains(skill)) {
                    eligibleResources.put(skill, resource);
                }
            }
        }
        return eligibleResources;
    }

    private Integer getResourceSkillNumber(List<Resource> resources, Skill skill) {
        return resources.stream()
                .mapToInt(resource -> resource.getSkills().contains(skill) ? 1 : 0)
                .sum();
    }

    private Boolean isSkillMatch(Map<Skill, Integer> taskRequiredSkills, Set<Skill> resourceSkills) {
        return resourceSkills.containsAll(taskRequiredSkills.keySet());
    }
}
