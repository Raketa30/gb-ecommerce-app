import {CommonService} from "./CommonService";
import {Product} from "../../../model/product";
import {Inject, InjectionToken} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ProductDAO} from "../interface/ProductDAO";
import {ProductSearchValues} from "../search/SearchObjects";
import {Observable} from "rxjs";

export const PRODUCT_URL_TOKEN = new InjectionToken<string>('product_url');

export class ProductService extends CommonService<Product> implements ProductDAO {
  constructor(@Inject(PRODUCT_URL_TOKEN) private baseUrl,
              httpClient: HttpClient) {
    super(baseUrl, httpClient);
  }

  findAllPaginated(productSearchValues: ProductSearchValues): Observable<Product> {
    return undefined;
  }
}
