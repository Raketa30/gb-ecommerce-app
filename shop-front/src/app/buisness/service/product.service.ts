import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  backendProductURI = environment.backendURL + "/api/v1/product";

  constructor(private httpClient: HttpClient) {
  }

  public getProductList(): Observable<any> {
    return this.httpClient.get<any>(this.backendProductURI + '/list');
  }
}
