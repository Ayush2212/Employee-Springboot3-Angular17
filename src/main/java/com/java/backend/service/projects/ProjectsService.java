package com.java.backend.service.projects;

import com.java.backend.entity.Employee;
import com.java.backend.entity.Projects;

import java.util.List;
import java.util.Optional;

public interface ProjectsService {
//    void assignEmployeeToProject(int projectId, int employeeId);
public void assignEmployeesToProject(Projects project, List<Employee> employees);
    public Optional<Projects> getProjectById(int projectId);

    Projects save(Projects project);
}
