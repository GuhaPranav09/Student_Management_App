import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { IStudentDto } from 'src/app/models/StudentDto';
import { StudentService } from 'src/app/services/student.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css'],
})
export class ReportComponent {
  public isRecordsShown: boolean = true;
  constructor(
    private studentservice: StudentService,
    private formBuilder: FormBuilder
  ) {
    this.searchForm = this.formBuilder.group({
      startDate: ['', Validators.required],
      endDate: [''],
    });
  }
  public students: IStudentDto[] = [];
  public startDate = null;
  public endDate = null;
  public records: any[] = [];

  searchForm!: FormGroup;

  filterData() {

    this.startDate = this.searchForm.value.startDate
    this.endDate = this.searchForm.value.endDate

    if (this.startDate == "" && this.endDate == "") {
      Swal.fire({
        position: 'center',
        icon: 'error',
        text: 'Both date fields are mandatory!',
        showConfirmButton: false,

        timer: 2000,

      });
    } else if (this.startDate == "") {
      Swal.fire({
        position: 'center',
        icon: 'error',
        text: 'Start date field is mandatory!',
        showConfirmButton: false,

        timer: 2000,

      });
    
    }
    else if (this.endDate == "") {
       Swal.fire({
        position: 'center',
        icon: 'error',
        text: 'End date field is mandatory!',
        showConfirmButton: false,

        timer: 2000,

      });
    }
    else if (this.startDate != null && this.endDate != null) {
      if (this.startDate > this.endDate) {
        Swal.fire({
          position: 'center',
          icon: 'error',
          text: 'End date should be greater than or equal to Start Date!',
          showConfirmButton: false,
          timer: 2000,

        });
           }
      else {
        this.studentservice
          .getStudentsByDates(this.searchForm.value.startDate, this.searchForm.value.endDate)
          .subscribe((data) => {
            console.log(data);
            this.students = data;
            this.isRecordsShown = false;
          });
      }
    }

  }
  downloadFile(filePath: string) {
    // const link = document.createElement('a');
    // link.href = filePath;
    // link.download = 'your_file_name.xlsx';
    // document.body.appendChild(link);
    // link.click();
    // document.body.removeChild(link);
    const link = document.createElement('a');
    link.href = filePath;
    link.target = '_blank'; // Open in a new tab/window
    link.download = 'Training Curriculum.xlsx'; // Set a default filename

    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }

  onClickGenerateExcel() {
    if (this.startDate == null || this.endDate == null) {
      //alert('Both date fields are mandatory!');
    } else {
      this.studentservice
        .generateExcel(this.startDate, this.endDate)
        .subscribe((response: Blob) => {
          // Create a blob from the response data
          const blob = new Blob([response], {
            type: 'application/vnd.ms-excel',
          });

          // For other browsers
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = 'report.xls';
          document.body.appendChild(a);
          a.click();

          // Cleanup
          window.URL.revokeObjectURL(url);
          document.body.removeChild(a);
          Swal.fire({
            position: 'center',
            icon: 'success',
            text: 'Report Downloded Successfully',
            showConfirmButton: false,
            timer: 2000,
          });
        });
    }
  }
}