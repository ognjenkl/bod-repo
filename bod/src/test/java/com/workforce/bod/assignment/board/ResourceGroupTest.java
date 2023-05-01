//package com.workforce.bod;
//
//import com.workforce.bod.ResourceGroup;
//import com.workforce.bod.assignment.Resource;
//import com.workforce.bod.assignment.Skill;
//import com.workforce.bod.assignment.Task;
//import com.workforce.bod.assignment.TaskGroup;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Map;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ResourceGroupTest {
//
//    private ResourceGroup resourceGroup;
//
//    @BeforeEach
//    void setUp() {
//        resourceGroup = new ResourceGroup();
//    }
//
//    @Test
//    void givenResourceGroup_whentGetResources_thenNotNull() {
//        assertNotNull(resourceGroup.getResources());
//    }
//
//    @Test
//    void givenResourceWithSkillConstruction_whenAddResource_thenSkillsNotEmpty() {
//        Resource resource = new Resource();
//        resource.addSkill(Skill.CONSTRUCTION);
//        resourceGroup.addResource(resource);
//
//        assertNotNull(resourceGroup.getSkills());
//        assertTrue(resourceGroup.getResources().size() > 0);
//        assertTrue(resourceGroup.getSkills().size() > 0);
//    }
//
//    @Test
//    void givenResourceWithTwoSkills_whenAddResource_thenSkillsNotEmpty() {
//        Resource resource = new Resource();
//        resource.addSkill(Skill.CONSTRUCTION);
//        resource.addSkill(Skill.DEMOLITION);
//        resourceGroup.addResource(resource);
//
//        assertTrue(resourceGroup.getResources().size() > 0);
//        assertTrue(resourceGroup.getSkills().size() > 0);
//    }
//}
