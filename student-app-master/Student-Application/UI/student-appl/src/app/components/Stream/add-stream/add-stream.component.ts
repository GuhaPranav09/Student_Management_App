import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Stream } from 'src/app/models/Stream';
import { StreamService } from 'src/app/services/stream.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-stream',
  templateUrl: './add-stream.component.html',
  styleUrls: ['./add-stream.component.css'],
})
export class AddStreamComponent {
  public stream: Stream = new Stream();
  private destroy$ = new Subject<void>();
  constructor(private router: Router, private service: StreamService) {}
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  addStream(form: NgForm) {
    // Check if the form is valid
    if (form.valid) {
      // Proceed with saving if the form is valid
      this.service
        .saveStream(this.stream)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {
            this.router.navigate(['dashboard/stream']);
          },
          error: (err) => {
            // Handle error response
            console.error('Error adding stream', err);
            Swal.fire({
              position: 'center',
              icon: 'error',
              text: 'Stream already exist',
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
  gotoStreams() {
    this.router.navigate(['dashboard/stream']);
  }
}
