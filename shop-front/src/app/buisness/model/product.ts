export class Product {
  id: number;
  title: string;
  description: string;
  cost: number;
  imageLink: string;

  constructor(id: number, title: string, description: string, cost: number, imageLink: string) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.cost = cost;
    this.imageLink = imageLink;
  }
}
