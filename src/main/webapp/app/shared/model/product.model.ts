export interface IProduct {
  id?: number;
  name?: string;
  price?: string;
  branch?: string;
  color?: string;
}

export class Product implements IProduct {
  constructor(public id?: number, public name?: string, public price?: string, public branch?: string, public color?: string) {}
}
