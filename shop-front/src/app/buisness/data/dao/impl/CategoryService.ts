import {Inject, Injectable, InjectionToken} from "@angular/core";
import {CommonService} from "./CommonService";
import {Category} from "../../../model/category";
import {CategoryDAO} from "../interface/CategoryDAO";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

export const CATEGORY_URL_TOKEN = new InjectionToken<string>('category_url');

@Injectable({
  providedIn: 'root'
})

export class CategoryService extends CommonService<Category> implements CategoryDAO {

  constructor(@Inject(CATEGORY_URL_TOKEN) private baseUrl: string,
              private http: HttpClient) {
    super(baseUrl, http);
  }

  getCategoryList(): Observable<Category[]> {
    return this.http.get<Category[]>(this.baseUrl + '/list');
  }
}
