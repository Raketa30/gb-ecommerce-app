import {CommonDAO} from "./CommonDAO";
import {Product} from "../../../model/product";
import {Observable} from "rxjs";
import {ProductSearchValues} from "../search/SearchObjects";

export interface ProductDAO extends CommonDAO<Product> {
  findAllPaginated(productSearchValues: ProductSearchValues): Observable<Product[]>;

  findByCategoryId(id: string): Observable<Product[]>;
}
