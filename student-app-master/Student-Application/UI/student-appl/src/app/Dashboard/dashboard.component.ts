import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { IUser, User } from '../models/User';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent {
  public profile?: string | null = '';
  public user: IUser = new User();
  public isSettingsDisplayed: boolean = false;

  constructor(private router: Router, private authService: AuthService) {
    // var e = localStorage.getItem('user');

    var e = authService.getUser();
    if (e) {
      console.log();
      this.user = JSON.parse(e);
    } else {
      console.error('User Not Found');
    }

    // this.profile = 'SUPERVISOR';
    // this.profile = 'ADMIN';
  }
  logout() {
    localStorage.clear();
    this.router.navigate(['login']);
  }

  expandSettings() {
    if (this.isSettingsDisplayed === true) {
      this.isSettingsDisplayed = false;
    } else {
      this.isSettingsDisplayed = true;
    }
  }
}
