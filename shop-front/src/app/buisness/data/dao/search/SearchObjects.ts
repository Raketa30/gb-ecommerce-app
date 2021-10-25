
export class ProductSearchValues {
  sortDirection: string = 'ASC';
  sortField: string = 'id';

  pageNum: number = 1;
  pageSize: number = 9;
  pageNumbers: number[] = [1];

  //filters
  isFiltered = false;
  title: string = '';
  minPrice: number = 0;
  maxPrice: number = 99999999999;
  categoryIds: number[] = [];
}
