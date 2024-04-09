package com.java.backend.controller;

import com.java.backend.dto.EmployeeDto;
import com.java.backend.entity.Employee;
import com.java.backend.entity.Projects;
import com.java.backend.service.EmployeeService;
import com.java.backend.service.projects.ProjectsService;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/com")
public class EmployeeController {
    @Autowired
    private ModelMapper mapper;
 
    
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectsService projectsService;
    @PostMapping("/save/employee")
    public ResponseEntity<Object> saveEmployee(@RequestBody EmployeeDto employee) {
        try {
            Employee savedEmployee = employeeService.saveEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save employee: " + e.getMessage());
        }
    }

    @GetMapping("/searchBySalaryRange")
    public ResponseEntity<List<Employee>> searchEmployeesBySalaryRange(
            @RequestParam(name = "minSalary") double minSalary,
            @RequestParam(name = "maxSalary") double maxSalary
    ) {
        try {
            List<Employee> employeesInRange = employeeService.findEmployeesBySalaryRange(minSalary, maxSalary);
            return ResponseEntity.status(HttpStatus.OK).body(employeesInRange);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get/employee")
    public ResponseEntity<Object> getEmployees() {
        try {
            Optional<List<Employee>> allEmployees = employeeService.getEmployees();
            if (allEmployees.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(allEmployees.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch employees: " + e.getMessage());
        }
    }
    @GetMapping("/get/employee/{employeeId}")
    //get mey mil bhi skta h nhi bhi mil skta h so use Optional nd try catch mey Response enity
    public ResponseEntity<Object> getEmployee(@PathVariable Integer employeeId) {
        try{
            Optional<Employee> employeeById=employeeService.getEmployeeById(employeeId);
            if(employeeById.isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(employeeById.get());
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found" +null);
            }

        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No employee for Given Id " + employeeId + e.getMessage());
        }
//        return employeeService.getEmployees(employeeId);
    }

    @GetMapping("/department/{departmentName}/average-salary")
    public Double getAverageSalaryByDepartment(@PathVariable String departmentName) {
        double salary=employeeService.getAverageSalaryByDepartment(departmentName);
        System.out.println("average salary is"+ salary);
        return salary;
    }

    @DeleteMapping("/delete/employee/{employeeId}")
    public void deleteEmployee(@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

    @PutMapping("/update/employee")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @PutMapping("update/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer employeeId, @RequestBody EmployeeDto updatedEmployeeDto) {
        Employee updatedEmployee = convertToEntity(updatedEmployeeDto);
        Employee updated = employeeService.updateEmployeeById(employeeId, updatedEmployee);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Helper method to convert DTO to Entity
    private Employee convertToEntity(EmployeeDto employeeDTO) {
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Employee employee= mapper.map(employeeDTO, Employee.class);
//        Employee employee = new Employee();
        // employee.setEmployee_name(employeeDTO.getEmployeeName());
        // employee.setEmpl_contact_number(employeeDTO.getEmpContactNumber());
        // employee.setEmployee_address(employeeDTO.getEmployeeAddress());
        // employee.setEmployee_gender(employeeDTO.getEmployeeGender());
        // employee.setEmployee_depart(employeeDTO.getEmployeeDepartment());
        // employee.setEmployee_skills(employeeDTO.getEmployeeSkills());
        return employee;
    }


    // Create a new employee and assign them to an existing project
//    @PostMapping("/createEmployeeForProject/{projId}")
//    public Employee createEmployeeForProject(@PathVariable int projId, @RequestBody Employee employee) {
//        Projects project = projectsService.getProjectById(projId);
//        return employeeService.createEmployeeForProject(employee, project);
//    }

    // Fetch some existing employees and assign them to an existing project
//    @PostMapping("/assignEmployeeToProject/{projId}")
//    public void assignEmployeeToProject(@PathVariable int projId, @RequestBody List<Integer> employeeIds) {
//        Projects project = projectsService.getProjectById(projId);
//        List<Employee> employees = employeeService.getEmployeesByIds(employeeIds);
//        projectService.assignEmployeesToProject(employees, project);
//    }


}
