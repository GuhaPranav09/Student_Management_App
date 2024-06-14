import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { IUser, User } from 'src/app/models/User';
import { Userservice } from 'src/app/services/userservice.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  public user: IUser = new User();

  private destroy$ = new Subject<void>();

  constructor(private router: Router, private userService: Userservice) {}

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  addUser(form: NgForm) {
    alert;
    if (form.valid) {
      if (this.user.isAdmin === '') {
        this.user.isAdmin = 'N';
        this.user.role = 'USER';
      }

      this.userService
        .registerUser(this.user)
        .pipe(takeUntil(this.destroy$))

        .subscribe({
          next: (resp) => {
            console.log(resp);

            console.log(resp.msg);

            this.router.navigate(['/login']);
          },

          error: (err) => {
            // console.error('Error while adding user: {}', err);
            let errorMsg = 'Something went wrong. Please try again later.';
            if (err && err.error && err.error.error) {
              errorMsg = err.error.error;
            }
            //alert(errorMsg);
          },
        });
    } else {
      //alert('user => ' + JSON.stringify(this.user));
      Object.keys(form.controls).forEach((key) => {
        form.controls[key].markAsTouched(); // Mark each field as touched to trigger error messages
      });
    }
  }

  backToLogin() {
    this.router.navigateByUrl('/login');
  }
}
