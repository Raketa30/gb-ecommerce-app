import {CommonDAO} from "./CommonDAO";
import {Category} from "../../../model/category";
import {Observable} from "rxjs";

export interface CategoryDAO extends CommonDAO<Category> {
  getCategoryList(): Observable<Category[]>
}
