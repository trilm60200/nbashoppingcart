import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPriceAudit } from 'app/shared/model/price-audit.model';

type EntityResponseType = HttpResponse<IPriceAudit>;
type EntityArrayResponseType = HttpResponse<IPriceAudit[]>;

@Injectable({ providedIn: 'root' })
export class PriceAuditService {
  public resourceUrl = SERVER_API_URL + 'api/price-audits';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPriceAudit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPriceAudit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(priceAudit: IPriceAudit): IPriceAudit {
    const copy: IPriceAudit = Object.assign({}, priceAudit, {
      createDate: priceAudit.createDate && priceAudit.createDate.isValid() ? priceAudit.createDate.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createDate = res.body.createDate ? moment(res.body.createDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((priceAudit: IPriceAudit) => {
        priceAudit.createDate = priceAudit.createDate ? moment(priceAudit.createDate) : undefined;
      });
    }
    return res;
  }
}
