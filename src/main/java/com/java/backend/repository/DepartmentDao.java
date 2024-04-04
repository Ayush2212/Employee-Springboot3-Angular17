package com.java.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.backend.entity.Department;

@Repository
public interface DepartmentDao extends JpaRepository<Department, Long> {
    //add custom queries as per business logics

}