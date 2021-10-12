import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private adminPanelUri = environment.backendURL + '/admin';

  constructor(private httpClient: HttpClient) {
  }

  public getHttpTraces(): Observable<any> {
    return this.httpClient.get(`${this.adminPanelUri}/httptrace`);
  }
}
