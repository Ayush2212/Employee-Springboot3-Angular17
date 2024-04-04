package com.java.backend.dto;

import lombok.Data;


import java.util.Date;

@Data
public class AddReviewRequest {
    
    private Long employeeId;

    
    private Date reviewDate;


    private String description;
}
