import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee.model';
import { EmployeeService } from '../employee.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  employeeId: number | null = null;
  employees: []=[];
  dataSource: any;
  startDate: Date | null = null;
  endDate: Date | null = null;

  displayedColumns: string[] = ['reviewDate', 'reviewDescription'];

  constructor(private employeeService: EmployeeService,
    private router: Router, private route: ActivatedRoute) {
      
    //this.getEmployeeList();
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id !== null) {
        this.employeeId = +id;
        this.getEmployeeReview();
      } else {
        // Handle the case when id is null (optional)
      }
    });
  }

  getEmployeeReview(): void{
    if (this.employeeId !== null) {
      this.employeeService.getEmployeeReview(this.employeeId).subscribe(data => {
        this.dataSource = data.reviews;
      });
    }
  }

  searchReviews(): void {
    if (this.employeeId && this.startDate && this.endDate) {
      this.employeeService.getEmployeeReviewsByDate(this.employeeId, this.startDate, this.endDate).subscribe(reviews => {
        this.dataSource = reviews;
      });
    }
  }

 

}


// JPA Query complex wali 
// Open API generator
// Microservoce Project 
// create frontend pages
// Aggregation Functions and Grouping: When you need to perform aggregation functions (like COUNT, SUM, AVG, etc.) or grouping operations in your queries, native queries can provide more flexibility and control over the results compared to using standard repository methods.

// Bulk Operations: For bulk update or delete operations on a large number of records, native queries can be more efficient than fetching entities one by one and updating/deleting them using Spring Data JPA methods.