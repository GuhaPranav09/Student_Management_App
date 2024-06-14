import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Stream } from 'src/app/models/Stream';
import { StreamService } from 'src/app/services/stream.service';

@Component({
  selector: 'app-edit-stream',
  templateUrl: './edit-stream.component.html',
  styleUrls: ['./edit-stream.component.css'],
})
export class EditStreamComponent {
  public stream: Stream = new Stream();

  private destroy$ = new Subject<void>();

  constructor(private router: Router, private streamService: StreamService) {}

  changeCmnStatus(status: string) {
    //alert(status + ' - ' + this.stream.cmnStatusId);
  }

  ngOnInit(): void {
    this.stream = history.state.stream;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  updateStream(form: NgForm) {
    if (form.valid) {
      this.streamService
        .updateStream(this.stream)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {
            this.router.navigate(['dashboard/stream']);
          },
          error: (err) => {
            // Handle error response
            console.error('Error updating stream', err);
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

  goToStreams() {
    this.router.navigate(['dashboard/stream']);
  }
}
