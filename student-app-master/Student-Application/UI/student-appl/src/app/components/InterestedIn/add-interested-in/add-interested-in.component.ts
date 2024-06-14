import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { InterestedIn } from 'src/app/models/InterestedIn';
import { InterestedInService } from 'src/app/services/interested-in.service';

@Component({
  selector: 'app-add-interested-in',
  templateUrl: './add-interested-in.component.html',
  styleUrls: ['./add-interested-in.component.css'],
})
export class AddInterestedInComponent {
  public interestedIn: InterestedIn = new InterestedIn();
  private destroy$ = new Subject<void>();
  constructor(private router: Router, private service: InterestedInService) {}
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  addInterestedIn(form: NgForm) {
    // Check if the form is valid
    if (form.valid) {
      // Proceed with saving if the form is valid
      this.service
        .saveInterestedIn(this.interestedIn)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {
            this.router.navigate(['dashboard/interestedIn']);
          },
          error: (err) => {
            // Handle error response
            console.error('Error adding interestedIn', err);
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
  gotoInterestedIns() {
    this.router.navigate(['dashboard/interestedIn']);
  }
}
