package com.java.backend.repository;

import com.java.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
    //add custom queries as per business logics
    List<Employee> findBySalaryBetween(double minSalary, double maxSalary);

    @Query(value = "SELECT AVG(e.salary) FROM employee e JOIN department_table d ON e.depart_id = d.id WHERE d.department_name = :departmentName", nativeQuery = true)
    Double findAverageSalaryByDepartment(@Param("departmentName") String departmentName);
}
