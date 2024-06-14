import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Specialization } from 'src/app/models/Specialization';
import { SpecilizationDto } from 'src/app/models/SpecializationDto';
import { SpecializationService } from 'src/app/services/specialization.service';

@Component({
  selector: 'app-specialization',
  templateUrl: './specialization.component.html',
  styleUrls: ['./specialization.component.css'],
})
export class SpecializationComponent {
  public profile?: string | null = '';
  public specializationsDto: SpecilizationDto[] = [];
  private destroy$ = new Subject<void>();

  constructor(
    private router: Router,
    private Specializationervice: SpecializationService
  ) {
    this.getallSpecialization();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngOnInit(): void {}

  editSpecialization(specialization: Specialization) {
    this.router.navigate(['dashboard/editSpecialization'], {
      state: { specialization: specialization },
    });
  }

  getallSpecialization() {
    this.Specializationervice.getAllSpecializations()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp) => {
          console.log(JSON.stringify(resp));
          
          this.specializationsDto = resp;
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  addSpecialization() {
    this.router.navigate(['dashboard/addSpecialization']);
  }

  removeSpecialization(specializationId: number) {
    this.Specializationervice.deleteSpecialization(specializationId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp: any) => {
          this.getallSpecialization();
        },
        error: (err: any) => {
          console.error(err);
          this.getallSpecialization();
        },
      });
  }
}
