package com.workforce.bod.assignment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TaskGroup {

    Set<Task> tasks = new HashSet<>();
    Map<Skill, Integer> skills = new HashMap<>();

    public Set<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        addSkillsFrom(task);
    }

    private void addSkillsFrom(Task task) {
        addSkillsToMap(skills, task);
    }

    public Map<Skill, Integer> getSkills() {
        return skills;
    }

    public void recalculateSkills() {
//        skills.clear();
//        for(Task task : tasks) {
//            addSkillsFrom(task);
//        }
        Map<Skill, Integer> map = generateNewRecalculationMap(tasks);
        skills = map;
    }

    Map<Skill, Integer> generateNewRecalculationMap(Set<Task> tasks) {
        Map<Skill, Integer> map = new HashMap<>();
        for (Task task : tasks) {
            addSkillsToMap(map, task);
        }
        return map;
    }

    private void addSkillsToMap(Map<Skill, Integer> map, Task task) {
        for (Map.Entry<Skill, Integer> entry : task.getRequiredSkills().entrySet()) {
            if (map.containsKey(entry.getKey())) {
                map.put(entry.getKey(), map.get(entry.getKey()) + entry.getValue());
            } else {
                map.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        removeSkillsFrom(task);
    }

    private void removeSkillsFrom(Task task) {
        for(Map.Entry<Skill, Integer> entry : task.getRequiredSkills().entrySet()) {
            if(skills.containsKey(entry.getKey())) {
                skills.put(entry.getKey(), skills.get(entry.getKey()) - entry.getValue());
                if (skills.get(entry.getKey()) <= 0) {
                    skills.remove(entry.getKey());
                }
            } else {
                throw new IllegalStateException("TaskGroup does not contain skill " + entry.getKey());
            }
        }
    }

    public boolean verifySkills(TaskGroup taskGroup) {
        Map<Skill, Integer> map = generateNewRecalculationMap(tasks);
        return map.equals(taskGroup.getSkills());
    }
}
