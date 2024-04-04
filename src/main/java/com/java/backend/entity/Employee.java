package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Employee class is owning side entity with Review and Project entities
@Entity
@Table(name="employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String employee_name;
    private String empl_contact_number;
    private String employee_address;
    private String employee_gender;
    private String employee_depart;
    private String employee_skills;
    private double salary;
    @Column(name = "hire_date")
    @Temporal(TemporalType.DATE)
    private Date hireDate;

    //  @OneToMany(cascade = CascadeType.ALL)
    //  @JoinColumn(name = "employee_id", referencedColumnName = "id")
    // private List<Review> reviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="depart_id")
    private Department department;
    
    @ManyToMany
     @JoinTable(name = "EMPLOYEE_PROJECT_TABLE", joinColumns = @JoinColumn(name = "emp_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id"))
       private Set<Projects> projects = new HashSet<>();


    public int calculateTenureInYears() {
        // Calculate tenure based on hire date
        return 0; // Placeholder, implement actual logic
    }

}
