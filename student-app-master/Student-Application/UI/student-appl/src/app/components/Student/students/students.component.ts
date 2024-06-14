import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { IStudentDto } from 'src/app/models/StudentDto';
import { IUser, User } from 'src/app/models/User';
import { AuthService } from 'src/app/services/auth.service';

import { StudentService } from 'src/app/services/student.service';
import { pageSizeOptions } from 'src/app/utils/util';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css'],
})
export class StudentsComponent {
  public profile?: string | null = '';
  public students: IStudentDto[] = [];

  public user: IUser = new User();
  public isSerchingByStdName: boolean = false;
  public isSerchingByRefferedStdName: boolean = false;
  public page: number = 0;
  public size: number = 10;
  public totalPages: number = 0;
  public first: boolean = true;
  public last: boolean = false;
  public pageSizes: number[] = pageSizeOptions;
  private destroy$ = new Subject<void>();
  public flag: boolean = false;
  referName: string = '';
  studentName: string = '';
 public count: number=0;


  constructor(
    private router: Router,
    private studentservice: StudentService,
    private authService: AuthService
  ) {

    this.getallstudents();
     var e = authService.getUser();

    if (e) {
      console.log();
      this.user = JSON.parse(e);
    } else {
      console.error('User Not Found');
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngOnInit(): void { }

  editStudent(student: IStudentDto) {
    this.router.navigate(['dashboard/editStudent'], {
      state: { student: student },
    });
  }

  getallstudents() {
    this.studentservice
      .getAllStudentsPage(this.page, this.size)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp) => {
          this.count=resp.count;
          this.students = resp.records;
          this.totalPages = resp.totalPages;
          this.first = resp.first;
          this.last = resp.last;
          // this.page = 0;
         
          console.log(this.students.length);
          
          if(this.count>10)
          {
            console.log("true");
            this.flag=true;
          }
          else{
            console.log("false");
            this.flag=false;
          }

          // if(this.students.length>=this.size)
          // {
          //   this.flag=true;
          // }


        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  addStudent() {
    this.router.navigate(['dashboard/addStudent']);
  }

  removeStudent(studentsId: number) {
    this.studentservice
      .deleteStudent(studentsId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp: any) => {
          console.log(resp);


          Swal.fire({
            position: 'center',
            icon: 'success',
            text: 'Student deleted Successfully...',
            showConfirmButton: false,
            timer: 2000,
          });

          setTimeout(() => {
            this.getallstudents();
          }, 2000)
        },
        error: (err: any) => {
          console.error(err);
          this.getallstudents();
        },
      });
  }
  getStudentsByReferName() {
    this.isSerchingByStdName = false;
    this.isSerchingByRefferedStdName = true;
    this.studentservice
      .getStudentByReferedName(this.referName, this.page, this.size)
      .subscribe(
        (resp: any) => {
          console.log(resp);
          this.students = resp.records;
          this.totalPages = resp.totalPages;
          this.first = resp.first;
          this.last = resp.last;
          // this.page = 0;

          this.count=resp.count;
          if(this.count>10)
          {
            console.log("true");
            this.flag=true;
          }
          else{
            console.log("false");
            this.flag=false;
          }
          

        },
        (err: any) => {
          console.error(err);

          this.getallstudents();
        }
      );
  }

  getStudentsBystdName() {
    this.isSerchingByRefferedStdName = false;
    this.isSerchingByStdName = true;
    this.studentservice
      .getStudentsByStdName(this.studentName, this.page, this.size)
      .subscribe(
        (resp: any) => {
          console.log(resp);
          this.students = resp.records;
          this.totalPages = resp.totalPages;
          this.first = resp.first;
          this.last = resp.last;
          // this.page = 0;

          this.count=resp.count;  
          if(this.count>10)
          {
            console.log("true");
            this.flag=true;
          }
          else{
            console.log("false");
            this.flag=false;
          }

        },
        (err: any) => {
          console.error(err);
          this.getallstudents();
          //  //alert("dat not found")
        }
      );
  }

  printSize(event: any) {
    this.size = event.target.value;
    this.page = 0;
    if (this.isSerchingByStdName) {
      this.getStudentsBystdName();
    } else if (this.isSerchingByRefferedStdName) {
      this.getStudentsByReferName();
    } else {
      this.getallstudents();
    }
  }

  goToPage(pageNumber: number) {
    this.page = pageNumber;
    if (this.isSerchingByStdName) {
      this.getStudentsBystdName();
    } else if (this.isSerchingByRefferedStdName) {
      this.getStudentsByReferName();
    } else {
      this.getallstudents();
    }
  }

  goToNextOrPreviousr(direction: string) {
    // this.page = direction === 'forward' ? this.page + 1 : this.page - 1;

    if (direction === 'forward' && !this.last) {
      this.page += 1;
      console.log(this.page)
    } else if (direction === 'backward' && !this.first) {
      console.log(this.page)

      this.page -= 1; 

      console.log(this.page)
    }
    if (this.isSerchingByStdName) {
      this.getStudentsBystdName();
    } else if (this.isSerchingByRefferedStdName) {
      this.getStudentsByReferName();
    } else {
      this.getallstudents();
    }
    this.studentservice.getStudentsByStdName(this.studentName, 1, 10).subscribe(
      (resp: any) => {
        console.log(resp);
        this.students = resp;
      },
      (err: any) => {
        console.error(err);
        this.getallstudents();
        //  //alert("dat not found")
      }
    );
  }
  

}

function getStudentsBystdName() {
  throw new Error('Function not implemented.');
}
