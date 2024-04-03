import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Employee } from './employee.model';
import { Observable } from 'rxjs';
import { Review } from './review.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private httpClient: HttpClient) { }

  api = "http://localhost:8085"

  public saveEmployee(employee: Employee): Observable<Employee> {
    return this.httpClient.post<Employee>(`${this.api}/com/save/employee`, employee);
  }

  public getEmployees(): Observable<Employee[]> {
      return this.httpClient.get<Employee[]>(`${this.api}/com/get/employee`);
  }
  searchEmployees(searchQuery: string): Observable<Employee[]> {
    return this.httpClient.get<Employee[]>(`${this.api}/employees?search=${searchQuery}`);
  }
  public deleteEmployee(employeeId: number) {
    return this.httpClient.delete(`${this.api}/com/delete/employee/${employeeId}`);
  }

  public getEmployee(employeeId: number) {
    return this.httpClient.get<Employee>(`${this.api}/com/get/employee/${employeeId}`);
  }

  public updateEmployee(employee: Employee) {
    return this.httpClient.put<Employee>(`${this.api}/com/update/employee`, employee);
  }

  public getEmployeeReview(employeeId: number): Observable<{ employeeData: Employee, reviews: any[] }> {
    return this.httpClient.get<{ employeeData: Employee, reviews: any[] }>(`${this.api}/api/review/employeeReview/${employeeId}`);

}

getEmployeeReviewsByDate(employeeId: number, startDate: Date, endDate: Date): Observable<Review[]> {
  const url = `${this.api}/api/review/employeeReviewByDate/${employeeId}`;
  const params = { startDate: startDate.toISOString(), endDate: endDate.toISOString() };
  return this.httpClient.get<Review[]>(url, { params });
}
}
