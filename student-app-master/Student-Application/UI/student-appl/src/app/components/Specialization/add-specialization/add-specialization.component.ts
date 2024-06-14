import { Component } from '@angular/core';

import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Specialization } from 'src/app/models/Specialization';
import { Stream } from 'src/app/models/Stream';
import { SpecializationService } from 'src/app/services/specialization.service';
import { StreamService } from 'src/app/services/stream.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-specialization',
  templateUrl: './add-specialization.component.html',
  styleUrls: ['./add-specialization.component.css'],
})
export class AddSpecializationComponent {
  public specialization: Specialization = new Specialization();

  public streams: Stream[] = [];
  private destroy$ = new Subject<void>();
  constructor(private router: Router, private service: SpecializationService ,
    private streamService:StreamService ,
    ) {
      this.getallstreams();
    }
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
   
  }

  addSpecialization(form: NgForm) {
    // Check if the form is valid
    if (form.valid) {
      // Proceed with saving if the form is valid
      this.service
        .saveSpecialization(this.specialization)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {
            Swal.fire({
              position: 'center',
              icon: 'success',
              text: 'Specialization added Successfully...',
              showConfirmButton: false,
              timer: 1500,
            });
            setTimeout(() => {
              this.router.navigate(['dashboard/specialization']);
            },2000)
           
          },
          error: (err) => {
            // Handle error response
            console.error('Error adding specializations', err);
            Swal.fire({
              position: 'center',
              icon: 'error',
              text: 'Specialization Name  already exist',
              showConfirmButton: false,
              timer: 2000,
            });
          },
        });
    } else {
      // If the form is not valid, it means required fields are empty
      // Display error messages to prompt the user to fill them
      Object.keys(form.controls).forEach((key) => {
        form.controls[key].markAsTouched(); // Mark each field as touched to trigger error messages
      });
    }
  }
  
  gotoSpecialization() {
    this.router.navigate(['dashboard/specialization']);
  }


  getallstreams() {
    this.streamService
      .getAllStreams()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp) => {
          console.log(resp);
          
          this.streams = resp;
        },
        error: (err) => {
          console.error(err);
        },
      });
  }
}
