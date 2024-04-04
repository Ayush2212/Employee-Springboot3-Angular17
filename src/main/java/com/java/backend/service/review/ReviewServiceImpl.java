package com.java.backend.service.review;

import com.java.backend.dto.AddReviewRequest;
import com.java.backend.dto.EmployeeDto;
import com.java.backend.dto.EmployeeReviewDto;
import com.java.backend.dto.ReviewDto;
import com.java.backend.entity.Employee;
import com.java.backend.entity.Review;
import com.java.backend.repository.EmployeeDao;
import com.java.backend.repository.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private ReviewDao reviewDao;


    //Save api 
    @Override
    public ReviewDto saveReview(int empId, ReviewDto reviewDto){
        //first check employee present on given Id or not
        Optional<Employee> optionalEmployee= employeeDao.findById(empId);
        if(optionalEmployee.isPresent()){
            Employee emp= optionalEmployee.get();
            Review saveReview = new Review();
            //saveReview.setEmployee(emp);
            saveReview.setReviewDate(reviewDto.getReviewDate());
            saveReview.setDescription(reviewDto.getComments());
            reviewDao.save(saveReview);
            // emp.getReviews().add(saveReview);
            // employeeDao.save(emp);
            //System.out.print("Employe table data"+ emp.toString());
            return reviewDto;
        }
        else{
            // return response null;
            throw new IllegalArgumentException("Employee not found for ID: " + empId);
        }
    }
    // @Override
    // public List<Review> getReviewsByEmpId(Long empId) {
    //     return reviewDao.findByEmp_Id(empId);
    // }
    // // public EmployeeReviewDto getEmployeeReviews(int employeeId) {
    // @Override
    // public EmployeeReviewDto getEmployeeReviews(int employeeId) {


    @Override
    public Review addReview(AddReviewRequest request) {
        Long employeeId = request.getEmployeeId();
Integer employeeIdInteger = employeeId.intValue(); // Convert Long to Integer
Optional<Employee> optionalEmployee = employeeDao.findById(employeeIdInteger);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            Review review = new Review();
            review.setReviewDate(request.getReviewDate());
            review.setDescription(request.getDescription());
        
            review.setEmployee(employee); // Set the employee ID in the review entity
            return reviewDao.save(review); // Save the review entity
        } else {
            throw new RuntimeException("Employee not found with ID: " + request.getEmployeeId());
        }
    }


    @Override
    public List<Review> getReviewsByEmpId(Long employeeId) {
      return reviewDao.findByEmployee_id(employeeId);
    }


    @Override
    public Review updateReviewById(Long id, Review updatedReview) {
        Optional<Review> existingReviewOptional = reviewDao.findById(id);
        if (existingReviewOptional.isPresent()) {
            Review existingReview = existingReviewOptional.get();
            existingReview.setReviewDate(updatedReview.getReviewDate());
            existingReview.setDescription(updatedReview.getDescription());
            // Update other fields as needed
            return reviewDao.save(existingReview);
        } else {
            throw new RuntimeException("Review not found with ID: " + id);
        }
    }


    @Override
    public void deleteReviewById(Long id) {
        if (reviewDao.existsById(id)) {
            reviewDao.deleteById(id);
        } else {
            throw new RuntimeException("Review not found with ID: " + id);
        }
    }
    
    //     throw new UnsupportedOperationException("Unimplemented method 'getEmployeeReviews'");
    // }




















    
        
    //     long empId= employeeId;
    //     Employee employee = employeeDao.findById(employeeId).orElse(null);
    //     if (employee == null) {
    //         throw new RuntimeException("Employee not found with ID: " + employeeId);
    //     }
    //     //List<Review>revieById= reviewDao.findAllById((Long)employeeId);
    //     EmployeeDto employeeDto= new EmployeeDto();
    //     employeeDto.setEmployeeName(employee.getEmployee_name());
    //     employeeDto.setEmployeeDepartment(employee.getEmployee_depart());
    //     employeeDto.setEmpContactNumber(employee.getEmpl_contact_number());
    //     employeeDto.setSalary(employee.getSalary());
    //     employeeDto.setReviews(employee.getReviews());


    //     EmployeeReviewDto empReviewDto = new EmployeeReviewDto();
    //     empReviewDto.setEmployeeData(employeeDto);
    //     //empReviewDto.setSalary(employee.getSalary());

    //     // List<Review> reviews = employee.getReviews();
    //     // List<ReviewDto> reviewDtos = new ArrayList<>();
    //     // for (Review review : reviews) {
    //     //     ReviewDto reviewDto = new ReviewDto();
    //     //     reviewDto.setReviewDate(review.getReviewDate());
    //     //     reviewDto.setComments(review.getDescription());
    //     //     reviewDtos.add(reviewDto);
    //     // }
    //     // empReviewDto.setReviews(reviewDtos);

    //     return empReviewDto;
    // }
    
}
