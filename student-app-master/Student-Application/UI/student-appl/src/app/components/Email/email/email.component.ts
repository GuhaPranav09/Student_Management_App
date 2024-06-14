import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { takeUntil } from 'rxjs';
import { Email } from 'src/app/models/Email';
import { StudentService } from 'src/app/services/student.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent {

  public email: Email = new Email();
  constructor(private router: Router, private service: StudentService) {}
  submitForm(form: NgForm) {
    if (form.valid) {
      this.service.sendEmail(this.email).subscribe({
        next: (resp) => {
          
          Swal.fire({
            position: 'center',
            icon: 'success',
            text: 'Email sent successfully...',
            showConfirmButton: false,
            timer: 1500,
          });
          console.log('Email sent successfully', resp);
          // Optionally, reset the form after successful submission
          form.resetForm();
        },
        error: (err) => {
          Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Error',
            text: 'Error sending email',
            showConfirmButton: false,
            timer: 2000,
          });
          console.error('Error sending email', err);
        },
      });
    } else {
      // Mark all form fields as touched to trigger error messages
      Object.keys(form.controls).forEach((key) => {
        form.controls[key].markAsTouched();
      });
    }
  }
  resetForm() {
    this.email = {
      toAddress: '',
      ccAddress:'',
      emailsubject: '',
      emailBody: ''
    };
  }

}
