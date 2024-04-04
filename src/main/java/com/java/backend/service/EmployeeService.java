package com.java.backend.service;

import com.java.backend.entity.Employee;
import com.java.backend.repository.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public Employee saveEmployee(Employee employee) throws Exception {
        //return employeeDao.save(employee);
        try{
            Employee savedEmployee= employeeDao.save(employee);
            return savedEmployee;
        } catch (Exception e){
            throw new Exception("Error saving employee: " + e.getMessage());
        }

    }

//    public List<Employee> getEmployees() {
//        List<Employee> employees = new ArrayList<>();
//        employeeDao.findAll().forEach(employees::add);
//        return employees;
//    }

    public List<Employee> findEmployeesBySalaryRange(double minSalary, double maxSalary) {
        return employeeDao.findBySalaryBetween(minSalary, maxSalary);
    }

    public Optional<List<Employee>> getEmployees() {
    try {
        List<Employee> employees = new ArrayList<>();
        employeeDao.findAll().forEach(employees::add);
        if (!employees.isEmpty()) {
            return Optional.of(employees);
        } else {
            return Optional.empty();
        }
    } catch (Exception e) {
        throw new RuntimeException("Error fetching employees: " + e.getMessage());
    }
}

    public Optional<Employee> getEmployeeById(Integer employeeId) {
        try {
            Optional<Employee> employeeById = employeeDao.findById(employeeId);
            if (employeeById.isPresent()) {
                return employeeById;
            } else {
                // Employee not found for the given ID
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching employee by Id: " + employeeId + ", " + e.getMessage());
        }

    }

    public void deleteEmployee(Integer employeeId) {
        employeeDao.deleteById(employeeId);
    }

    public Employee updateEmployee(Employee employee) {
        employeeDao.findById(employee.getId()).orElseThrow();
        return employeeDao.save(employee);
    }

    public Employee updateEmployeeById(Integer employeeId, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeDao.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setEmployee_name(updatedEmployee.getEmployee_name());
            existingEmployee.setEmpl_contact_number(updatedEmployee.getEmpl_contact_number());
            existingEmployee.setEmployee_address(updatedEmployee.getEmployee_address());
            existingEmployee.setEmployee_gender(updatedEmployee.getEmployee_gender());
          existingEmployee.setEmployee_depart(updatedEmployee.getEmployee_depart());
           existingEmployee.setEmployee_skills(updatedEmployee.getEmployee_skills());
            return employeeDao.save(existingEmployee);
        } else {
            return null; // Employee with given ID not found
        }
    }


    public Optional<List<Employee>> getEmployeesByIds(List<Integer> employeeIds) {

        if (employeeIds == null || employeeIds.isEmpty()) {
            return Optional.empty(); // Return empty optional if the list is null or empty
        }

        List<Employee> employees = employeeDao.findAllById(employeeIds);
        if (employees.isEmpty()) {
            return Optional.empty(); // Return empty optional if no employees are found
        }

        return Optional.of(employees);
    }
}
