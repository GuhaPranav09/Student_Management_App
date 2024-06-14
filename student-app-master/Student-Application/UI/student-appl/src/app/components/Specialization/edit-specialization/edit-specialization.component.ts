import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Specialization } from 'src/app/models/Specialization';
import { SpecilizationDto } from 'src/app/models/SpecializationDto';
import { SpecializationService } from 'src/app/services/specialization.service';

@Component({
  selector: 'app-edit-specialization',
  templateUrl: './edit-specialization.component.html',
  styleUrls: ['./edit-specialization.component.css'],
})
export class EditSpecializationComponent {
  // public specialization: Specialization = new Specialization();
  public specialization: SpecilizationDto = new SpecilizationDto();
  changeCmnStatus(status: string) {
    // //alert(status + ' - ' + this.specialization.cmnStatusId);
  }

  private destroy$ = new Subject<void>();

  constructor(
    private router: Router,
    private specializationService: SpecializationService
  ) { }

  ngOnInit(): void {
    this.specialization = history.state.specialization;

  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  updateSpecialization(form: NgForm) {
    if (form.valid) {
      console.log('specialization updated ser');
      this.specializationService
        .updateSpecialization(this.specialization)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {
            this.router.navigate(['dashboard/specialization']);
          },
          error: (err) => {
            // Handle error response
            console.error('Error updating specialization', err);
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

  goToSpecializations() {
    this.router.navigate(['dashboard/specialization']);
  }
}
