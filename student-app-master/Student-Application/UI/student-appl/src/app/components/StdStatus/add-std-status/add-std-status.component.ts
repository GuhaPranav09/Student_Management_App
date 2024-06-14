import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { StdStatus } from 'src/app/models/StdStatus';
import { StdStatusService } from 'src/app/services/std-status.service';

@Component({
  selector: 'app-add-std-status',
  templateUrl: './add-std-status.component.html',
  styleUrls: ['./add-std-status.component.css'],
})
export class AddStdStatusComponent {
  public stdstatus: StdStatus = new StdStatus();
  private destroy$ = new Subject<void>();
  constructor(private router: Router, private service: StdStatusService) {}
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  addStdStatus(form: NgForm) {
    // Check if the form is valid
    if (form.valid) {
      // Proceed with saving if the form is valid
      this.service
        .saveStdStatus(this.stdstatus)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {
            console.log('added');
            this.router.navigate(['dashboard/stdStatus']);
          },
          error: (err) => {
            // Handle error response
            console.error('Error adding stdstatus', err);
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
  gotoStdStatus() {
    this.router.navigate(['dashboard/stdStatus']);
  }
}
