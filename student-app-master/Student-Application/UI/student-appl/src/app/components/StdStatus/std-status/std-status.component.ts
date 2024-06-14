import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { StdStatus } from 'src/app/models/StdStatus';
import { CmnStatusService } from 'src/app/services/cmn-status.service';
import { SpecializationService } from 'src/app/services/specialization.service';
import { StdStatusService } from 'src/app/services/std-status.service';
import { StreamService } from 'src/app/services/stream.service';

@Component({
  selector: 'app-std-status',
  templateUrl: './std-status.component.html',
  styleUrls: ['./std-status.component.css'],
})
export class StdStatusComponent {
  public profile?: string | null = '';
  public stdStatus: StdStatus[] = [];
  private destroy$ = new Subject<void>();

  constructor(
    private router: Router,
    private stdStatusService: StdStatusService
  ) {
    this.getallstdStatus();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngOnInit(): void {}

  editStdStatus(status: StdStatus) {
    this.router.navigate(['dashboard/editStdStatus'], {
      state: { status: status },
    });
  }

  getallstdStatus() {
    this.stdStatusService
      .getAllStdStatus()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp) => {
          this.stdStatus = resp;
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  addStdStatus() {
    this.router.navigate(['dashboard/addStdStatus']);
  }

  removeStdStatus(stdStatusId: String) {
    this.stdStatusService
      .deleteStdStatus(stdStatusId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp: any) => {
          this.getallstdStatus();
        },
        error: (err: any) => {
          console.error(err);
          this.getallstdStatus();
        },
      });
  }
}
