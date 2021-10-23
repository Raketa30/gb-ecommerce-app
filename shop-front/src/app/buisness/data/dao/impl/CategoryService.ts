import {Inject, InjectionToken} from "@angular/core";
import {CommonService} from "./CommonService";
import {Category} from "../../../model/category";
import {CategoryDAO} from "../interface/CategoryDAO";
import {HttpClient} from "@angular/common/http";

export const CATEGORY_URL_TOKEN = new InjectionToken<string>('category_url');

export class CategoryService extends CommonService<Category> implements CategoryDAO {

  constructor(@Inject(CATEGORY_URL_TOKEN) baseUrl: string,
              httpClient: HttpClient) {
    super(baseUrl, httpClient);
  }
}
