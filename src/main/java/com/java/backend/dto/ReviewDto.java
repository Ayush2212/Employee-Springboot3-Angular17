package com.java.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewDto {
    private int employeeId;
    private Date reviewDate;
    private String comments;
}
