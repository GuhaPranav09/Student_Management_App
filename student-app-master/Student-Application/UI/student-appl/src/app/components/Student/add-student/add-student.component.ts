import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { IInterestedIn } from 'src/app/models/InterestedIn';
import { ISpecialization } from 'src/app/models/Specialization';
import { IStdStatus } from 'src/app/models/StdStatus';
import { IStream, Stream } from 'src/app/models/Stream';
import { Student } from 'src/app/models/Student';
import { IStudentDto, StudentDto } from 'src/app/models/StudentDto';
import { CmnStatusService } from 'src/app/services/cmn-status.service';
import { InterestedInService } from 'src/app/services/interested-in.service';
import { SpecializationService } from 'src/app/services/specialization.service';
import { StdStatusService } from 'src/app/services/std-status.service';
import { StreamService } from 'src/app/services/stream.service';
import { StudentService } from 'src/app/services/student.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-student',
  templateUrl: './add-student.component.html',
  styleUrls: ['./add-student.component.css'],
})
export class AddStudentComponent implements OnInit, OnDestroy {
  public student: Student = new Student();
  public students: IStudentDto[] = [];
  public streams: IStream[] = [];
  public specializations: ISpecialization[] = [];
  // public stdStatus: IStdStatus[] = [];
  public interestedIn: IInterestedIn[] = [];
  public isLoading = false;
 


  private destroy$ = new Subject<void>();
  constructor(
    private router: Router,
    private service: StudentService,
    private streamService: StreamService,
    private specializationService: SpecializationService,
    private stdstatusService: StdStatusService,
    private studentService: StudentService,
    private interestedInService: InterestedInService
  ) {}
  ngOnInit(): void {
    this.getStreams();
    this.getSpecialization(this.student.streamId);
    // this.getStdStatus();
    this.getStudents();
    this.getInterestedIn();
    const specializationElement = document.getElementById("specializationId") as HTMLSelectElement;
    if (specializationElement) {
      specializationElement.disabled = true;
    }
    
    
    
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

 
  addStudent(form: NgForm) {
    this.isLoading=true
    if (form.valid) {
      console.log(JSON.stringify(this.student));
      this.service
        .saveStudent(this.student)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {

            Swal.fire({
              position: 'center',
              icon: 'success',
              text: 'Student Created Successfully...',
              showConfirmButton: false,
              timer: 2000,
            });
            setTimeout(() => {
              this.router.navigate(['dashboard/student']);
            },2000)
            this.isLoading=false
          },
          error: (err) => {
            console.error('Error adding student', err);

            Swal.fire({
              position: 'center',
              icon: 'error',
              text: 'Email already exist',
              showConfirmButton: false,
              timer: 2000,

            });
            this.isLoading=false
          },
        });
    } else {
      Object.keys(form.controls).forEach((key) => {
        form.controls[key].markAsTouched(); // Mark each field as touched to trigger error messages
        this.isLoading=false;
      });
    }
  }
  getStudents() {
    this.studentService.getAllStudents().subscribe({
      next: (resp) => {
        this.students = resp;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  getStreams() {
    this.streamService.getAllStreams().subscribe({
      next: (resp) => {
        this.streams = resp;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  streamChange() {
    this.getSpecialization(this.student.streamId);
  
  }

  getSpecialization(streamId: number) {
    if (this.student.streamId !== 0) {
      this.specializationService
        .getAllSpecializationsByStreamId(streamId)
        .subscribe({
          next: (resp) => {
            this.specializations = resp;
          },
          error: (err) => {
            console.error(err);
          },
        });
    } else {
      this.specializationService.getAllSpecializations().subscribe({
        next: (resp) => {
          this.specializations = resp;
        },
        error: (err) => {
          console.error(err);
        },
      });
    }
  }
  // getStdStatus() {
  //   this.stdstatusService.getAllStdStatus().subscribe({
  //     next: (resp) => {
  //       this.stdStatus = resp;
  //     },
  //     error: (err) => {
  //       console.error(err);
  //     },
  //   });
  // }

  getInterestedIn() {
    this.interestedInService.getAllInterestedIn().subscribe({
      next: (resp) => {
        this.interestedIn = resp;
        console.log('intersedin');
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  gotoStudents() {
    this.router.navigate(['dashboard/student']);
  }
}
