import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Userservice } from '../services/userservice.service';
import { ILogin, ILoginResponse, IUser, Login, User } from '../models/User';
import { AuthService } from '../services/auth.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  // public login: Login = new Login('', '');
  // public user: user = new user(0, '', '', '', '', '', 0, '', '');
  public isForgotPassword: boolean = false;
  public isLogin: boolean = false;
  public forOtp: boolean = false;
  public forNewPassword: boolean = false;
  public Titile: string = '';
  public otp: string = '';
  public newPassword: string = '';
  public otpInvalid: boolean = true;
  public otpvalid: boolean = true;
  public forgotPasswordEmail: string = '';
  public showMessage: string = '';

  public userLogin: ILogin = new Login();
  public user: IUser = new User();

  constructor(
    private router: Router,
    private userService: Userservice,
    private authService: AuthService
  ) {
    this.isLogin = true;
    // this.Titile = 'Login';
  }
  Register() {
    this.router.navigateByUrl('/register');
  }

  loginUser(form: NgForm) {
    if (form.valid) {
      this.userService.login(this.userLogin).subscribe({
        next: (data: ILoginResponse) => {
          console.log(data);
          console.log(form.value);
          this.user = data.userBean;
          console.log(this.user);
          this.authService.setAccessToken(data.access_token);
          this.authService.setRole(data.userBean.role);
          this.authService.setUser(JSON.stringify(data.userBean));

          // Swal.fire({
          //   position: 'center',
          //   icon: 'success',
          //   title: 'Logged in Successfully...',
          //   showConfirmButton: false,
          //   timer: 1500,
          // });

          this.router.navigateByUrl('dashboard');
        },
        error: (err) => {
          console.error(err);
          Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Error',
            text: 'Invalid Credentials or Something Went Wrong , Try Again',
            showConfirmButton: false,
            timer: 2000,
          });
        },
      });
    } else {
      Object.keys(form.controls).forEach((key) => {
        form.controls[key].markAsTouched(); // Mark each field as touched to trigger error messages
      });
    }
  }

  forgotPassword() {
    this.isForgotPassword = !this.isForgotPassword;
    this.Titile = 'Forgot Password';
    this.isLogin = !this.isLogin;
  }
  backToForgotPassword() {
    this.isForgotPassword = !this.isForgotPassword;
    this.Titile = 'Forgot Password';
    this.forOtp = !this.forOtp;
  }
  goToPwdChangePage() {
    this.Titile = 'VERIFY OTP';
    this.isForgotPassword = !this.isForgotPassword;
    this.isLogin = false;
    this.forOtp = !this.forOtp;
  }
  sendResetPasswordEmail(form: NgForm) {
    if (form.valid) {
      this.userService.forgotPassword(this.forgotPasswordEmail).subscribe({
        next: (response) => {
          setTimeout(() => {
            this.showMessage = '';
          }, 2000);

          this.Titile = 'VERIFY OTP';
          this.isForgotPassword = !this.isForgotPassword;
          this.isLogin = false;
          this.forOtp = !this.forOtp;
        },
        error: (err) => {
          this.showMessage = 'Invalid Email';
          setTimeout(() => {
            this.showMessage = '';
          }, 2000);
          console.error('Error occurred:', err);
        },
      });
    } else {
      Object.keys(form.controls).forEach((key) => {
        form.controls[key].markAsTouched();
      });
    }
  }

  verifyOtp(form: NgForm) {
    if (form.valid) {
      if (this.otp != null && this.otpvalid) {
        this.userService
          .verifyOtp(this.forgotPasswordEmail, this.otp)
          .subscribe({
            next: (response) => {
              this.Titile = 'RESET';
              this.otpvalid = false;
              this.otpInvalid = false;
            },
            error: (err) => {
              this.showMessage = 'Invalid OTP';
              setTimeout(() => {
                this.showMessage = '';
              }, 2000);
              this.Titile = 'VERIFY OTP';
              this.isForgotPassword = false;
              this.isLogin = false;
              console.error('Error occurred:', err);
            },
          });
      } else {
        this.userService
          .confirmPassword(this.forgotPasswordEmail, this.newPassword)
          .subscribe({
            next: (response) => {
              this.isLogin = true;
              this.Titile = 'Login';
              this.forNewPassword = false;
              this.forOtp = false;
            },
            error: (err) => {
              console.error('Error occurred:', err);
            },
          });
      }
    } else {
      Object.keys(form.controls).forEach((key) => {
        form.controls[key].markAsTouched();
      });
    }
  }
}
