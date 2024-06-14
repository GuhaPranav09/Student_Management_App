export interface IUser {
  userId: number;
  name: string;
  userEmail: string;
  userContact: string;
  cmnStatusId: string;
  userPassword: string;
  isAdmin: string;
  role: string;
}

export class User implements IUser {
  userId: number = 0;
  name: string = '';
  userEmail: string = ' ';
  userContact: string = '';
  cmnStatusId: string = '';
  userPassword: string = '';
  isAdmin: string = '';
  role = '';
}

export interface ILogin {
  email: string;
  password: string;
}

export class Login implements ILogin {
  email: string = '';
  password: string = '';
}

export interface ILoginResponse {
  userBean: User;
  access_token: string;
  refresh_token: string;
}
export class LoginResponse implements ILoginResponse {
  userBean = new User();
  access_token = '';
  refresh_token = '';
}
