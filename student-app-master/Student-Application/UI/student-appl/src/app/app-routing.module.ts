import { NgModule, inject } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './Dashboard/dashboard.component';
import { AuthGuard } from './Guards/auth-guard.guard';

import { StreamsComponent } from './components/Stream/streams/streams.component';
import { StudentsComponent } from './components/Student/students/students.component';
import { StdStatusComponent } from './components/StdStatus/std-status/std-status.component';
import { SpecializationComponent } from './components/Specialization/specialization/specialization.component';
import { InterestedInComponent } from './components/InterestedIn/interested-in/interested-in.component';
import { CmnStatusComponent } from './components/CmnStatus/cmn-status/cmn-status.component';
import { EditstudentComponent } from './components/Student/editstudent/editstudent.component';
import { EditStreamComponent } from './components/Stream/edit-stream/edit-stream.component';
import { AddStreamComponent } from './components/Stream/add-stream/add-stream.component';
import { AddStudentComponent } from './components/Student/add-student/add-student.component';
import { EditStdStatusComponent } from './components/StdStatus/edit-std-status/edit-std-status.component';
import { AddStdStatusComponent } from './components/StdStatus/add-std-status/add-std-status.component';
import { EditSpecializationComponent } from './components/Specialization/edit-specialization/edit-specialization.component';
import { AddSpecializationComponent } from './components/Specialization/add-specialization/add-specialization.component';

import { AddInterestedInComponent } from './components/InterestedIn/add-interested-in/add-interested-in.component';
import { EditInterestedInComponent } from './components/InterestedIn/edit-interested-in/edit-interested-in.component';
import { ReportComponent } from './components/Reports/report/report.component';
import { HomepageComponent } from './components/Home/homepage/homepage.component';
import { UsersComponent } from './components/User/users/users.component';
import { EditUserComponent } from './components/User/edit-user/edit-user.component';
import { AddUserComponent } from './components/User/add-user/add-user.component';
import { RegisterComponent } from './components/Register/register.component';
import { EmailComponent } from './components/Email/email/email.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Ensure default login redirection
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    // canActivate: [AuthGuard, SupervisorGuard],
    path: 'dashboard',
    canActivate: [AuthGuard],
    component: DashboardComponent,
    children: [
      { path: '', component: HomepageComponent },
      { path: 'home', component: HomepageComponent },
      { path: 'stream', component: StreamsComponent },
      { path: 'student', component: StudentsComponent },
      { path: 'stdStatus', component: StdStatusComponent },
      { path: 'specialization', component: SpecializationComponent },
      { path: 'interestedIn', component: InterestedInComponent },
      { path: 'cmnStatus', component: CmnStatusComponent },
      { path: 'editStudent', component: EditstudentComponent },
      { path: 'editstream', component: EditStreamComponent },
      { path: 'addStream', component: AddStreamComponent },
      { path: 'addStudent', component: AddStudentComponent },
      { path: 'editStdStatus', component: EditStdStatusComponent },
      { path: 'addStdStatus', component: AddStdStatusComponent },
      { path: 'addSpecialization', component: AddSpecializationComponent },
      { path: 'editSpecialization', component: EditSpecializationComponent },
      { path: 'addInterestedIn', component: AddInterestedInComponent },

      { path: 'editInterestedIn', component: EditInterestedInComponent },
      { path: 'email', component: EmailComponent },

      { path: 'users', component: UsersComponent },
      { path: 'edituser', component: EditUserComponent },
      { path: 'adduser', component: AddUserComponent },
      { path: 'report', component: ReportComponent },
    ],
  },
];


  @NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [],
})
  export class AppRoutingModule { }
