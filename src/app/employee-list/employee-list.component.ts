import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { Employee } from '../employee.model';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  employees: []=[];
  dataSource: Employee[] = [];
  searchQuery: string = '';

    displayedColumns: string[] = ['employeeId', 'employeeName', 'employeeContactNumber', 'employeeAddress', 'employeeDepartment', 'employeeGender', 'employeeSkills', 'salary', 'hireDate', 'edit', 'delete', 'viewDetails'];

  constructor(private employeeService: EmployeeService,
    private router: Router, private activatedRoute: ActivatedRoute) {
      
    this.getEmployeeList();
  }

  ngOnInit(): void {
    this.employees = this.activatedRoute.snapshot.data['employee-list'];
    console.log("Url Extracted data List compoent",this.employees);
  }

  updateEmployee(employeeId: number): void {
    this.router.navigate(['/employee', {employeeId: employeeId}]);
  }

  deleteEmployee(employeeId: number): void {
    console.log(employeeId);
    this.employeeService.deleteEmployee(employeeId).subscribe(
      {
        next: (res) => {
          console.log("employee list",res);
          this.getEmployeeList();
        },
        error: (err: HttpErrorResponse) => {
          console.log(err);
        }
      }
    );
  }

  getEmployeeList(): void {
    this.employeeService.getEmployees().subscribe(
      {
        next: (res: Employee[]) => {
          this.dataSource = res;
        },
        error: (err: HttpErrorResponse)=> {
          console.log(err);
        }
      }
    );
  }

  searchEmployees(): void {
    if (this.searchQuery.trim() !== '') {
      this.employeeService.searchEmployees(this.searchQuery).subscribe(
        {
          next: (res: Employee[]) => {
            this.dataSource = res;
          },
          error: (err: HttpErrorResponse) => {
            console.log(err);
          }
        }
      );
    } else {
      // If search query is empty, fetch all employees
      this.getEmployeeList();
    }
  }

  viewEmployeeDetails(employeeId: number): void {
    // Navigate to the employee details page with the employeeId as a route parameter
    this.router.navigate(['/details', employeeId]);
  }

}
