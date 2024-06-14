import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { CmnStatus } from 'src/app/models/CmnStatus';
import { CmnStatusService } from 'src/app/services/cmn-status.service';

@Component({
  selector: 'app-cmn-status',
  templateUrl: './cmn-status.component.html',
  styleUrls: ['./cmn-status.component.css'],
})
export class CmnStatusComponent {
  public profile?: string | null = '';
  public cmnStatus: CmnStatus[] = [];
  private destroy$ = new Subject<void>();

  constructor(
    private router: Router,
    private cmnStatusService: CmnStatusService
  ) {
    this.getallcmnStatus();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngOnInit(): void {}

  editCmnStatus(cmnStatus: CmnStatus) {
    this.router.navigate(['admindashboard/editcmnStatus'], {
      state: { cmnStatus: cmnStatus },
    });
  }

  getallcmnStatus() {
    this.cmnStatusService
      .getAllCmnStatus()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp) => {
          this.cmnStatus = resp;
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  addCmnStatus() {
    this.router.navigate(['admindashboard/addCmnStatus']);
  }

  removeCmnStatus(cmnStatusId: String) {
    this.cmnStatusService
      .deleteCmnStatus(cmnStatusId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp: any) => {
          this.getallcmnStatus();
        },
        error: (err: any) => {
          console.error(err);
          this.getallcmnStatus();
        },
      });
  }
}
