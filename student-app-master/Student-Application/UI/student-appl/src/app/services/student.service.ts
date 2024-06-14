import { Injectable } from '@angular/core';
import { Student } from '../models/Student';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { IStudentDto, StudentDto } from '../models/StudentDto';
import { environment } from 'src/environments/environment';
import { StudentApi } from '../api/api-paths';
import { IDashBoardDto } from '../models/DashboardDto';
import { IPageResponse } from '../models/PageResponse';
import { Email } from '../models/Email';

@Injectable({
  providedIn: 'root',
})
export class StudentService {
  constructor(private http: HttpClient) {}


  generateExcel(startDate: string, endDate?: string): Observable<Blob> {
    // Include responseType: 'blob' in the options object
    return this.http.get(`${environment.baseUrl}${StudentApi.GENERATE_EXCEL}/${startDate}/${endDate}`,{ responseType: 'blob' }
    );
  }
  //post
  saveStudent(student: Student): Observable<Student> {
    return this.http.post<Student>(
      `${environment.baseUrl}${StudentApi.SAVE}`,
      student
    );
  }

  //getall
  getAllStudents(): Observable<IStudentDto[]> {
    return this.http.get<IStudentDto[]>(
      `${environment.baseUrl}${StudentApi.ALLDTOS}`
    );
  }
  getAllStudentsPage(
    page: number,
    size: number
  ): Observable<IPageResponse<IStudentDto[]>> {
    return this.http.get<IPageResponse<IStudentDto[]>>(
      `${environment.baseUrl}${StudentApi.ALLDTOS}/${page}/${size}`
    );
  }

  //edit
  updateStudent(student: IStudentDto): Observable<string> {
    return this.http.put<string>(
      `${environment.baseUrl}${StudentApi.UPDATE}`,
      student
    );
  }

  //delete
  deleteStudent(studentId: number): Observable<void> {
    return this.http.delete<void>(
      `${environment.baseUrl}${StudentApi.DELETE}/${studentId}`
    );
  }

  getStudentById(studentId: number): Observable<Student> {
    return this.http.get<Student>(`${environment.baseUrl}/${studentId}`);
  }

  getStudentsByStdName(studentName: string, page: number, size: number) {
    return this.http.get<IPageResponse<IStudentDto[]>>(
      `${environment.baseUrl}${StudentApi.GETBYSTDNAME}/${studentName}/${page}/${size}`
    );
  }

  getStudentByReferedName(studentName: string, page: number, size: number) {
    return this.http.get<IPageResponse<IStudentDto[]>>(
      `${environment.baseUrl}${StudentApi.REFERENAME}/${studentName}/${page}/${size}`
    );
  }

  getAllDashboardDtos(): Observable<IDashBoardDto[]> {
    return this.http.get<IDashBoardDto[]>(
      `${environment.baseUrl}${StudentApi.COUNT}`
    );
    }

  getStudentsByDates(
    startDate: string,
    endDate?: string
  ): Observable<IStudentDto[]> {
    console.log(startDate + ' - ' + endDate);

    return this.http.get<IStudentDto[]>(
      `${environment.baseUrl}${StudentApi.FILTER}/${startDate}/${endDate}`
    );
  }

  onClickGenerateExcel(startDate: string, endDate?: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}${StudentApi.GENERATE_EXCEL}/${startDate}/${endDate}`
    );
  }

  getStudentsByStartDate(startDate: Date): Observable<IStudentDto[]> {
    return this.http.get<IStudentDto[]>(
      `${environment.baseUrl}${StudentApi.FILTERBYSTARTDATE}/${startDate}`
    );
  }
  getStudentsByEndDate(endDate: Date): Observable<IStudentDto[]> {
    return this.http.get<IStudentDto[]>(
      `${environment.baseUrl}${StudentApi.FILTERBYENDDATE}/${endDate}`
    );
  }
  // sendEmail
   sendEmail(email: Email): Observable<Student> {
    console.log(email)
    return this.http.post<Student>(
      `${environment.baseUrl}${StudentApi.EMAIL}`,
      email
    );
  }

  

}
