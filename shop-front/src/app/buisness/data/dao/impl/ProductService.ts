import {CommonService} from "./CommonService";
import {Product} from "../../../model/product";
import {Inject, Injectable, InjectionToken} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ProductDAO} from "../interface/ProductDAO";
import {ProductSearchValues} from "../search/SearchObjects";
import {Observable} from "rxjs";

export const PRODUCT_URL_TOKEN = new InjectionToken<string>('product_url');

@Injectable({
  providedIn: 'root'
})

export class ProductService extends CommonService<Product> implements ProductDAO {
  constructor(@Inject(PRODUCT_URL_TOKEN) private baseUrl,
              private http: HttpClient) {
    super(baseUrl, http);
  }

  findAllPaginated(productSearchValues: ProductSearchValues): Observable<any> {
    return this.http.post<any>(this.baseUrl + '/list', productSearchValues);
  }

  findByCategoryId(id: number): Observable<Product[]> {
    return this.http.post<any>(this.baseUrl + '/category-id', id);
  }

}
