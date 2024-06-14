import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { InterestedIn } from 'src/app/models/InterestedIn';
import { InterestedInService } from 'src/app/services/interested-in.service';

@Component({
  selector: 'app-edit-interested-in',
  templateUrl: './edit-interested-in.component.html',
  styleUrls: ['./edit-interested-in.component.css'],
})
export class EditInterestedInComponent {
  public interestedIn: InterestedIn = new InterestedIn();

  private destroy$ = new Subject<void>();

  constructor(private router: Router, private Service: InterestedInService) {}
  changeCmnStatus(status: string) {
    //alert(status + ' - ' + this.interestedIn.cmnStatusId);
  }

  ngOnInit(): void {
    this.interestedIn = history.state.interestedIn;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  updateInterestedIn(form: NgForm) {
    if (form.valid) {
      this.Service.updateInterestedIn(this.interestedIn)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {
            this.router.navigate(['dashboard/interestedIn']);
          },
          error: (err) => {
            // Handle error response
            console.error('Error updating InterestedIn', err);
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

  goToInterestedIns() {
    this.router.navigate(['dashboard/interestedIn']);
  }
}
