import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  public setAccessToken(accessToken: string) {
    localStorage.setItem('accessToken', accessToken);
  }

  public getAccessToken() {
    return localStorage.getItem('accessToken');
  }

  public setRefreshToken(refreshToken: string) {
    return localStorage.setItem('refreshToken', refreshToken);
  }

  public getRefreshToken() {
    localStorage.getItem('refreshToken');
  }

  public setUser(user: string) {
    localStorage.setItem('user', user);
  }

  public getUser() {
    return localStorage.getItem('user');
  }

  public setRole(role: string) {
    return localStorage.setItem('role', role);
  }

  public getRole() {
    return localStorage.getItem('role');
  }

  public clear() {
    localStorage.clear();
  }
  public isLoggedIn() {
    return this.getRole() && this.getAccessToken();
  }
}
