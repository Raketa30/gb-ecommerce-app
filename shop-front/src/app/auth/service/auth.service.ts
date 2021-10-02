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
    console.log(request);
    return this.httpClient.post<User>(this.backendAuthURI + '/login', request);
  }

  public registration(request: User): Observable<User> {
    console.log(request);
    return this.httpClient.post<User>(this.backendAuthURI + '/register', request);
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
