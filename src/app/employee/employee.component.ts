import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee.model';
import { NgForm } from '@angular/forms';
import { EmployeeService } from '../employee.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {

  isCreateEmployee: boolean = true;

  employee: any;

  skills: string[] = [];

  constructor(private employeeService: EmployeeService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {

  }

  ngOnInit(): void {

    this.employee = this.activatedRoute.snapshot.data['employee'];

    console.log("Url Extracted data",this.employee);
    console.log("employeLength", this.employee.length);

    if (this.employee && this.employee.id > 0) {
      this.isCreateEmployee = false;
      if (this.employee.employee_skills != '') {
        this.skills = [];
        this.skills = this.employee.employee_skills.split(',');
      }
    } else {
      this.isCreateEmployee = true;
    }

  }

  checkSkills(skill: string) {
    return this.employee.employee_skills != null && this.employee.employee_skills.includes(skill);
  }

  checkGender(gender: string) {
    return this.employee.employee_gender != null && this.employee.employee_gender == gender;
  }

  saveEmployee(employeeForm: NgForm): void {
    console.log("formData", employeeForm);

    if (this.isCreateEmployee) {
      this.employeeService.saveEmployee(this.employee).subscribe(
        {
          next: (res: Employee) => {
            console.log(res);
            employeeForm.reset();
            this.employee.employee_gender = '';
            this.skills = [];
            this.employee.employee_skills = '';
            this.router.navigate(["/employee-list"]);
          },
          error: (err: HttpErrorResponse) => {
            console.log(err);
          }
        }
      );
    } else {
      this.employeeService.updateEmployee(this.employee).subscribe(
        {
          next: (res: Employee) => {
            this.router.navigate(["/employee-list"]);
          },
          error: (err: HttpErrorResponse) => {
            console.log(err);
          }
        }
      );
    }
  }

  selectGender(gender: string): void {
    this.employee.employee_gender = gender;
  }

  onSkillsChanges(event: any): void {
    console.log(event);
    if (event.checked) {
      this.skills.push(event.source.value);
    } else {
      this.skills.forEach(
        (item, index) => {
          if (item == event.source.value) {
            this.skills.splice(index, 1);
          }
        }
      );
    }

    this.employee.employee_skills = this.skills.toString();
  }

}
