package com.java.backend.repository;

import com.java.backend.entity.Employee;
import com.java.backend.entity.Review;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDao extends JpaRepository<Review, Long> {

    //List<Review> findByEmp_Id(Long employeeId);
    List<Review> findByEmployee_id(Long employeeId);
    List<Review> findByEmployeeAndReviewDateBetween(Optional<Employee> employee, Date startDate, Date endDate);
}
