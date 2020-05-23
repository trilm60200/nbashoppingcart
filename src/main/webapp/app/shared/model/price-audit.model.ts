import { Moment } from 'moment';

export interface IPriceAudit {
  id?: number;
  productId?: number;
  productName?: string;
  price?: number;
  createDate?: Moment;
}

export class PriceAudit implements IPriceAudit {
  constructor(
    public id?: number,
    public productId?: number,
    public productName?: string,
    public price?: number,
    public createDate?: Moment
  ) {}
}
