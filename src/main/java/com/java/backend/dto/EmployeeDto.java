package com.java.backend.dto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.java.backend.entity.Projects;
import com.java.backend.entity.Review;
import lombok.Data;
@Data
public class EmployeeDto {
        private String employee_name;
        private String empl_contact_number;
        private String employee_address;
        private String employee_gender;
        private Long departmentId; 
        private String employee_skills;
        private double salary;
        private String hireDate;
        private List<Review> reviews;
        private Set<Projects> projects;
}
