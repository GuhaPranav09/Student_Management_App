import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { StdStatus } from 'src/app/models/StdStatus';
import { StdStatusService } from 'src/app/services/std-status.service';

@Component({
  selector: 'app-edit-std-status',
  templateUrl: './edit-std-status.component.html',
  styleUrls: ['./edit-std-status.component.css'],
})
export class EditStdStatusComponent {
  public stdStatus: StdStatus = new StdStatus();

  private destroy$ = new Subject<void>();

  constructor(
    private router: Router,
    private stdStatusService: StdStatusService
  ) {}

  ngOnInit(): void {
    this.stdStatus = history.state.status;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  changeCmnStatus(status: string) {
    //alert(status + ' - ' + this.stdStatus.cmnStatus);
  }

  updateStdStatus(form: NgForm) {
    if (form.valid) {
      console.log('StdStatus updated ser');
      this.stdStatusService
        .updateStdStatus(this.stdStatus)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {
            this.router.navigate(['dashboard/stdStatus']);
          },
          error: (err) => {
            // Handle error response
            console.error('Error updating StdStatus', err);
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

  goToStdStatuss() {
    this.router.navigate(['dashboard/stdStatus']);
  }
}
