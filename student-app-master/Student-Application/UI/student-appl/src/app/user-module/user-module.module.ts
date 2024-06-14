import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from '../Dashboard/dashboard.component';
import { RouterModule } from '@angular/router';

import { StreamsComponent } from '../components/Stream/streams/streams.component';
import { StudentsComponent } from '../components/Student/students/students.component';
import { StdStatusComponent } from '../components/StdStatus/std-status/std-status.component';
import { SpecializationComponent } from '../components/Specialization/specialization/specialization.component';
import { InterestedInComponent } from '../components/InterestedIn/interested-in/interested-in.component';
import { CmnStatusComponent } from '../components/CmnStatus/cmn-status/cmn-status.component';
import { EditstudentComponent } from '../components/Student/editstudent/editstudent.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EditStreamComponent } from '../components/Stream/edit-stream/edit-stream.component';
import { AddStreamComponent } from '../components/Stream/add-stream/add-stream.component';

import { AddStudentComponent } from '../components/Student/add-student/add-student.component';
import { EditStdStatusComponent } from '../components/StdStatus/edit-std-status/edit-std-status.component';
import { AddStdStatusComponent } from '../components/StdStatus/add-std-status/add-std-status.component';
import { EditSpecializationComponent } from '../components/Specialization/edit-specialization/edit-specialization.component';
import { AddSpecializationComponent } from '../components/Specialization/add-specialization/add-specialization.component';

import { UsersComponent } from '../components/User/users/users.component';
import { EditUserComponent } from '../components/User/edit-user/edit-user.component';
import { AddUserComponent } from '../components/User/add-user/add-user.component';
import { EditInterestedInComponent } from '../components/InterestedIn/edit-interested-in/edit-interested-in.component';
import { AddInterestedInComponent } from '../components/InterestedIn/add-interested-in/add-interested-in.component';
import { ReportComponent } from '../components/Reports/report/report.component';
import { HomepageComponent } from '../components/Home/homepage/homepage.component';
import { EmailComponent } from '../components/Email/email/email.component';
import { RegisterComponent } from '../components/Register/register.component';

@NgModule({
  declarations: [
    DashboardComponent,
    StreamsComponent,
    StudentsComponent,
    StdStatusComponent,
    SpecializationComponent,
    InterestedInComponent,
    CmnStatusComponent,
    EditstudentComponent,
    EditStreamComponent,
    AddStreamComponent,
    AddStudentComponent,
    EditStdStatusComponent,
    AddStdStatusComponent,
    EditSpecializationComponent,
    AddSpecializationComponent,
    UsersComponent,
    EditUserComponent,
    AddUserComponent,
    EditInterestedInComponent,
    AddInterestedInComponent,
    ReportComponent,
    HomepageComponent,
    EmailComponent,
    
  ],

  imports: [CommonModule,FormsModule,RouterModule,ReactiveFormsModule],
})
export class UserModule {}
