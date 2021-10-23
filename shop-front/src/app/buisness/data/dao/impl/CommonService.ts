import {CommonDAO} from "../interface/CommonDAO";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

export class CommonService<T> implements CommonDAO<T> {
  private readonly url: string;

  constructor(url: string, private httpClient: HttpClient) {
    this.url = url;
  }

  add(obj: T): Observable<T> {
    return this.httpClient.put<T>(this.url + "/add", obj);
  }

  delete(id: number): Observable<T> {
    return this.httpClient.post<T>(this.url + "/delete/", id);
  }

  findAll(): Observable<T[]> {
    return this.httpClient.get<any>(this.url + "/all");
  }

  findById(id: number): Observable<T> {
    return this.httpClient.post<T>(this.url + "/id", id);
  }

  update(obj: T): Observable<T> {
    return this.httpClient.patch<T>(this.url + "/update", obj);
  }

}
