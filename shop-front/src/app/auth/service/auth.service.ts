import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  backendAuthURI = environment.backendURL + '/api/v1/auth';

  constructor(private httpClient: HttpClient) {
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
}

export class User {
  id: number;
  username: string;
  email: string;
  password: string;
  roles: Array<Role>
}

export class Role {
  name: string;
}