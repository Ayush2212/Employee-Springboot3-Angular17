package com.java.backend.controller;

import com.java.backend.dto.AddReviewRequest;
import com.java.backend.dto.EmployeeDto;
import com.java.backend.dto.EmployeeReviewDto;
import com.java.backend.dto.ReviewDto;
import com.java.backend.entity.Employee;
import com.java.backend.entity.Review;
import com.java.backend.repository.EmployeeDao;
import com.java.backend.repository.ReviewDao;
import com.java.backend.service.review.ReviewService;
import com.java.backend.service.review.ReviewServiceImpl;

import jakarta.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/review")
public class ReviewController {
     @Autowired
    private ModelMapper mapper;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private EmployeeDao employeeDao;
    //Add employee review Api
    @PostMapping("/employee/{empId}")
    public ResponseEntity<ReviewDto>addReview(@PathVariable int empId, @RequestBody ReviewDto reviewDto){
        try{
           ReviewDto res= reviewService.saveReview(empId,reviewDto);
          
           return  ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
    //api to get review of an employee based on date
    //get all the reviews of an employee based on employee Id in the database
    //display all the reviews that contains word good or satisfactory

    //based on emp_id show return list of all reviews of an employee
    //here return object will contain employee name , salary etc, and review date and descriptions
    // @GetMapping("employee/{employeeId}")    
    // public EmpReviewDto getEmployeeReviewList(){

    //     }

    // @GetMapping("/employeeReview/{employeeId}")
    // public EmployeeReviewDto getEmployeeReviewList(@PathVariable int employeeId) {
    //     return reviewService.getEmployeeReviews(employeeId);
    // }

    // @GetMapping("/employeeReview/{employeeId}")
    // public List<Review> getEmployeeReviewList(@PathVariable Long employeeId) {
    //     return reviewService.getReviewsByEmpId(employeeId);
    // }

     @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestBody AddReviewRequest request) {
        Review review = reviewService.addReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

        //fetching employee data and all reviws
    @GetMapping("/employeeReview/{employeeId}")
    public EmployeeReviewDto getEmployeeReviewList(@PathVariable Long employeeId) {
        int employeeIdInt = Math.toIntExact(employeeId);
        Optional<Employee> employeeOptional= employeeDao.findById((int)employeeIdInt);

        if(employeeOptional.isPresent()){
              mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        EmployeeDto empDto= mapper.map(employeeOptional.get(), EmployeeDto.class);
            EmployeeReviewDto outpuDto= new EmployeeReviewDto();
            outpuDto.setEmployeeData(empDto);
            outpuDto.setReviews(reviewService.getReviewsByEmpId(employeeId));
            System.out.print(outpuDto.toString());
            return outpuDto;
        }
        else{
            return null;
        }
        
        
    }

    //retrieve reviwes according to Date
    @GetMapping("/employeeReviewByDate/{employeeId}")
    public List<Review> getEmployeeReviewList(
            @PathVariable int employeeId,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        // Find the employee by ID
        Optional<Employee> employee = employeeDao.findById(employeeId);

        // Check if the employee exists
        if(employee.isEmpty()){
            throw new RuntimeException("Employee not found with ID: " + employeeId);
        }

        // Retrieve reviews within the specified date range for the employee
        return reviewDao.findByEmployeeAndReviewDateBetween(employee, startDate, endDate);
    }


    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
        return reviewService.updateReviewById(id, updatedReview);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
    }

}
