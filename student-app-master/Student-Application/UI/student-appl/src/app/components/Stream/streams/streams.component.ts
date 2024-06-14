import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Stream } from 'src/app/models/Stream';
import { StreamService } from 'src/app/services/stream.service';

@Component({
  selector: 'app-streams',
  templateUrl: './streams.component.html',
  styleUrls: ['./streams.component.css'],
})
export class StreamsComponent {
  public profile?: string | null = '';
  public streams: Stream[] = [];
  private destroy$ = new Subject<void>();

  constructor(private router: Router, private streamService: StreamService) {
    this.getallstreams();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngOnInit(): void { }

  editStream(stream: Stream) {
    this.router.navigate(['dashboard/editstream'], {
      state: { stream: stream },
    });
  }

  getallstreams() {
    this.streamService
      .getAllStreams()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp) => {
          this.streams = resp;
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  addStream() {
    this.router.navigate(['dashboard/addStream']);
  }

  removeStream(streamId: number) {
    this.streamService
      .deleteStream(streamId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (resp: any) => {
          this.getallstreams();
        },
        error: (err: any) => {
          console.error(err);
          this.getallstreams();
        },
      });
  }
}
