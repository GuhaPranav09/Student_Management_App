import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { IInterestedIn } from 'src/app/models/InterestedIn';
import { ISpecialization } from 'src/app/models/Specialization';
import { IStdStatus } from 'src/app/models/StdStatus';
import { IStream } from 'src/app/models/Stream';
import { Student } from 'src/app/models/Student';
import { IStudentDto, StudentDto } from 'src/app/models/StudentDto';
import { InterestedInService } from 'src/app/services/interested-in.service';
import { SpecializationService } from 'src/app/services/specialization.service';
import { StdStatusService } from 'src/app/services/std-status.service';
import { StreamService } from 'src/app/services/stream.service';
import { StudentService } from 'src/app/services/student.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editstudent',
  templateUrl: './editstudent.component.html',
  styleUrls: ['./editstudent.component.css'],
})
export class EditstudentComponent {
  public student: StudentDto = new StudentDto();
  public students: IStudentDto[] = [];
  public streams: IStream[] = [];
  public specializations: ISpecialization[] = [];
  public stdStatus: IStdStatus[] = [];
  public interestedIn: IInterestedIn[] = [];

  private destroy$ = new Subject<void>();

  constructor(
    private router: Router,

    private streamService: StreamService,
    private specializationService: SpecializationService,
    private stdstatusService: StdStatusService,
    private studentService: StudentService,
    private interestedInService: InterestedInService
  ) {}

  ngOnInit(): void {
    this.student = history.state.student;
    this.getStreams();
    this.getSpecialization();
    this.getStdStatus();
    this.getStudents();
    this.getInterestedIn();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  updateStudent(form: NgForm) {
    if (form.valid) {
      this.studentService
        .updateStudent(this.student)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {
            
              Swal.fire({
                position: 'center',
                icon: 'success',
                text: 'Student Updated Successfully...',
                showConfirmButton: false,
                timer: 2000,
              });
          
            setTimeout(() => {
            this.router.navigate(['dashboard/student']);
          },2000)
          },
          error: (err) => {
            console.error('Error updating student', err);
          },
        });
    } else {
      //alert('error');
      Object.keys(form.controls).forEach((key) => {
        form.controls[key].markAsTouched();
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
  getSpecialization() {
    this.specializationService.getAllSpecializations().subscribe({
      next: (resp) => {
        this.specializations = resp;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
  getStdStatus() {
    this.stdstatusService.getAllStdStatus().subscribe({
      next: (resp) => {
        this.stdStatus = resp;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

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

  goToStudents() {
    this.router.navigate(['dashboard/student']);
  }
}
