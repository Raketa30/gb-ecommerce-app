export class Category {
  id: number;
  title: string;
  alias: string;

  constructor(id: number, title: string, alias: string) {
    this.id = id;
    this.title = title;
    this.alias = alias;
  }
}
