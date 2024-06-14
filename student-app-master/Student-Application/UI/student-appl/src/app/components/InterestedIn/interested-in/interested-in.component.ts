import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { InterestedIn } from 'src/app/models/InterestedIn';
import { InterestedInService } from 'src/app/services/interested-in.service';

@Component({
  selector: 'app-interested-in',
  templateUrl: './interested-in.component.html',
  styleUrls: ['./interested-in.component.css'],
})
export class InterestedInComponent {
  public profile?: string | null = '';
  public interestedIn: InterestedIn[] = [];
  private destroy$ = new Subject<void>();

  constructor(
    private router: Router,
    private interestedInservice: InterestedInService
  ) {
    this.getallInterestedIn();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngOnInit(): void {}

  editInterestedIn(interestedIn: InterestedIn) {
    this.router.navigate(['dashboard/editInterestedIn'], {
      state: { interestedIn: interestedIn },
    });
  }

  getallInterestedIn() {
    this.interestedInservice
      .getAllInterestedIn()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp) => {
          this.interestedIn = resp;
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  addInterestedIn() {
    this.router.navigate(['dashboard/addInterestedIn']);
  }

  removeInterestedIn(interestedInId: number) {
    this.interestedInservice
      .deleteInterestedIn(interestedInId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp: any) => {
          this.getallInterestedIn();
        },
        error: (err: any) => {
          console.error(err);
          this.getallInterestedIn();
        },
      });
  }
}
