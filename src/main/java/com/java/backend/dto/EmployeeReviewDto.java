package com.java.backend.dto;

import java.util.List;

import com.java.backend.entity.Employee;
import com.java.backend.entity.Review;

import lombok.Data;

@Data
public class EmployeeReviewDto{
   

    private EmployeeDto employeeData;
    private List<Review> reviews;

   // private double salary;
    //private List<ReviewDto> reviews;
}