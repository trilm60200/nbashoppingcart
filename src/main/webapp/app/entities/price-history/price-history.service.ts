import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPriceHistory } from 'app/shared/model/price-history.model';

type EntityResponseType = HttpResponse<IPriceHistory>;
type EntityArrayResponseType = HttpResponse<IPriceHistory[]>;

@Injectable({ providedIn: 'root' })
export class PriceHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/price-histories';

  constructor(protected http: HttpClient) {}

  create(priceHistory: IPriceHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(priceHistory);
    return this.http
      .post<IPriceHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(priceHistory: IPriceHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(priceHistory);
    return this.http
      .put<IPriceHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPriceHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPriceHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(priceHistory: IPriceHistory): IPriceHistory {
    const copy: IPriceHistory = Object.assign({}, priceHistory, {
      created_date:
        priceHistory.created_date && priceHistory.created_date.isValid() ? priceHistory.created_date.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created_date = res.body.created_date ? moment(res.body.created_date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((priceHistory: IPriceHistory) => {
        priceHistory.created_date = priceHistory.created_date ? moment(priceHistory.created_date) : undefined;
      });
    }
    return res;
  }
}
