import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isLoggedIn = false;
  currentUser = new BehaviorSubject<User>(null);

  backendAuthURI = environment.backendURL + '/api/v1/auth';

  constructor(private httpClient: HttpClient
  ) {
  }

  public login(request: User): Observable<User> {
    return this.httpClient.post<User>(this.backendAuthURI + '/login', request);
  }

  public registration(request: User): Observable<any> {
    return this.httpClient.put<User>(this.backendAuthURI + '/register', request);
  }

  public activateAccount(request: string): Observable<boolean> {
    return this.httpClient.post<boolean>(this.backendAuthURI + '/activate-account', request);
  }

  public sendResetPasswordEmail(request: string): Observable<boolean> {
    return this.httpClient.post<boolean>(this.backendAuthURI + '/send-reset-password-email', request);
  }

  public resetPassword(request: string, token: string): Observable<boolean> {
    const tokenParam = new HttpParams().set('token', token);
    return this.httpClient.post<boolean>(this.backendAuthURI + '/update-password', request, {params: tokenParam});
  }

  public logout(): Observable<boolean> {
    return this.httpClient.post<boolean>(this.backendAuthURI + '/logout', '');
  }
}

export class User {
  id: number;
  username: string;
  email: string;
  password: string;
  roles: Array<Role>
}

export class Role {
  id: number;
  name: string;
}
