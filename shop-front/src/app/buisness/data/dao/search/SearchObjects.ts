import {Product} from "../../../model/product";

export class ProductSearchValues {
  sortDirection: string = 'ASC';
  sortField: string = 'id';

  pageNum: number = 1;
  pageSize: number = 10;
  pageNumbers: number[] = [1];

  //product filters
  page: Product[];

}
