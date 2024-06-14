import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { IUser, User } from 'src/app/models/User';
import { Userservice } from 'src/app/services/userservice.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css'],
})
export class AddUserComponent {
  public user: IUser = new User();

  private destroy$ = new Subject<void>();
  constructor(private router: Router, private userService: Userservice) {}
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  addUser(form: NgForm) {
    if (form.valid) {
      if (this.user.isAdmin === '') {
        this.user.isAdmin = 'N';
        this.user.role = 'USER';
      }

      this.userService
        .saveUser(this.user)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {
            console.log(resp);
            console.log(resp.msg);

            Swal.fire({
              position: 'center',
              icon: 'success',
              text: 'User Added Successfully...'+
              ' please login with your email address and phone number',
              showConfirmButton: false,
              timer: 2000,
            });
            setTimeout(() => {
              this.router.navigate(['dashboard/users']);
            },2000)

            
          },

          error: (err) => {
            console.error('Error while adding user: {}', err);
            let errorMsg = 'Something went wrong. Please try again later.';
            if (err && err.error && err.error.error) {
              errorMsg = err.error.error;
            }
            // //alert(errorMsg);
            Swal.fire({
              // title: "The Internet?",
              text: `${errorMsg}`,
              icon: 'error',
            });
          },
        });
    } else {
      //alert('user => ' + JSON.stringify(this.user));
      Object.keys(form.controls).forEach((key) => {
        form.controls[key].markAsTouched(); // Mark each field as touched to trigger error messages
      });
    }
  }

  isAdmin() {
    if (this.user.isAdmin === '') {
      this.user.isAdmin = 'Y';
      this.user.role = 'ADMIN';
    }
  }
  gotoUsers() {
    this.router.navigate(['dashboard/users']);
  }
}
