package com.java.backend.service.review;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.backend.dto.AddReviewRequest;
import com.java.backend.dto.EmployeeReviewDto;
import com.java.backend.dto.ReviewDto;
import com.java.backend.entity.Review;

import jakarta.validation.Valid;


public interface ReviewService {
    //ReviewDto saveReview(Long empId, ReviewDto reviewDto);

    ReviewDto saveReview(int empId, ReviewDto reviewDto);

    Review addReview( AddReviewRequest request);

    //EmployeeReviewDto getEmployeeReviews(int employeeId);

    //  List<Review> getReviewsByEmpId(Long empId);
    List<Review>getReviewsByEmpId(Long employeeId);

    Review updateReviewById(Long id, Review updatedReview);

    void deleteReviewById(Long id);
}
