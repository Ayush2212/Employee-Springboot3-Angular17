package com.java.backend.repository;

import com.java.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
    //add custom queries as per business logics
    List<Employee> findBySalaryBetween(double minSalary, double maxSalary);
}
