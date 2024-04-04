package com.java.backend.service.projects;

import com.java.backend.entity.Employee;
import com.java.backend.entity.Projects;
import com.java.backend.repository.EmployeeDao;
import com.java.backend.repository.ProjectsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectsServiceImpl implements ProjectsService{

    @Autowired
   private ProjectsDao projectsDao;
    @Autowired
    private EmployeeDao employeeDao;
//    public void assignEmployeeToProject(int projectId, int employeeId) {
//        Projects project = projectsDao.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
//        Employee employee = employeeDao.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
//        project.getEmployees().add(employee);
//        projectsDao.save(project);
//    }

@Override
    public void assignEmployeesToProject(Projects project, List<Employee> employees) {
        project.getEmployees().addAll(employees);
        projectsDao.save(project);
    }
    @Override
    public Optional<Projects> getProjectById(int projectId){
        return projectsDao.findById(projectId);
    }

    @Override
    public Projects save(Projects project) {
        return projectsDao.save(project);

    }

}
