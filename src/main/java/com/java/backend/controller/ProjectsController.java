package com.java.backend.controller;

import com.java.backend.entity.Employee;
import com.java.backend.entity.Projects;
import com.java.backend.service.EmployeeService;
import com.java.backend.service.projects.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {
    @Autowired
    private ProjectsService projectsService ;
    @Autowired
    private EmployeeService employeeService;

//    In an existing project
//    add existing employees to this project (/createProjectForEmployees)
    @PostMapping("/{projectId}/assign-employeesToProject")
    public ResponseEntity<Object> assignEmployeesToProject(
            @PathVariable("projectId") int projectId,
            @RequestBody List<Integer> employeeIds) {
        try {
            // Validate project ID
            if (projectId <= 0) {
                return ResponseEntity.badRequest().body("Invalid project ID.");
            }

            // Validate employee IDs
            if (employeeIds == null || employeeIds.isEmpty() || employeeIds.stream().anyMatch(id -> id <= 0)) {
                return ResponseEntity.badRequest().body("Invalid employee IDs.");
            }

            Optional<Projects> projectsOptional = projectsService.getProjectById(projectId);
            if (projectsOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Project not found.");
            }

            Optional<List<Employee>> employeesOptional = employeeService.getEmployeesByIds(employeeIds);
            if (employeesOptional.isEmpty() || employeesOptional.get().isEmpty()) {
                return ResponseEntity.badRequest().body("No employees found with the given IDs.");
            }

            projectsService.assignEmployeesToProject(projectsOptional.get(), employeesOptional.get());
            return ResponseEntity.ok().body(projectsOptional.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    //create new project
    @PostMapping("/createProjects")
    public Projects createProject(@RequestBody  Projects project) {
        // You can add additional validation or business logic here if needed
        if (project == null || project.getProject_name() == null || project.getProject_name().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be empty.");
        }
        return projectsService.save(project);
    }

    }




