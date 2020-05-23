import { Moment } from 'moment';

export interface IPriceHistory {
  id?: number;
  product_id?: number;
  price?: number;
  created_date?: Moment;
}

export class PriceHistory implements IPriceHistory {
  constructor(public id?: number, public product_id?: number, public price?: number, public created_date?: Moment) {}
}
