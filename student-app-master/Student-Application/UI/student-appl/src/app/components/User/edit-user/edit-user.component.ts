import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { IUser, User } from 'src/app/models/User';
import { Userservice } from 'src/app/services/userservice.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent implements OnInit {
  public user: IUser = new User();
  public isCmnStatusChanged = true;
  public isAdminChanged = true;
  private destroy$ = new Subject<void>();

  constructor(private router: Router, private userService: Userservice) {}

  ngOnInit(): void {
    this.user = history.state.user;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  updateUser(form: NgForm) {
    if (form.valid) {
      this.userService
        .updateUser(this.user)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (resp) => {
            console.log(resp);
            Swal.fire({
              position: 'center',
              icon: 'success',
              text: 'User Details Updated Successfully...',
              showConfirmButton: false,
              timer: 2000,
            });
            setTimeout(() => {
              this.router.navigate(['dashboard/users']);
            },2000)
            
          },
          error: (err) => {
            // Handle error response
            console.error('Error while updating user', err);
          },
        });
    } else {
      Object.keys(form.controls).forEach((key) => {
        form.controls[key].markAsTouched();
      });
    }
  }

  isAdmin() {
    if (this.user.isAdmin === 'N') {
      this.user.isAdmin = 'Y';
    } else {
      this.user.isAdmin = 'N';
    }
    if (this.isAdminChanged === true) {
      this.isAdminChanged = false;
    } else {
      this.isAdminChanged = true;
    }
  }
  changeStatus() {
    if (this.user.cmnStatusId === 'A') {
      this.user.cmnStatusId = 'I';
    } else {
      this.user.cmnStatusId = 'A';
    }
    if (this.isCmnStatusChanged === true) {
      this.isCmnStatusChanged = false;
    } else {
      this.isCmnStatusChanged = true;
    }
  }
  changeCmnStatus(status: string) {
    //alert(status + ' - ' + this.user.cmnStatusId);
  }

  comnStatusChange() {}

  goToUsers() {
    this.router.navigate(['dashboard/users']);
  }
}
