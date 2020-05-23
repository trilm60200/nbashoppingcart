import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPriceAudit, PriceAudit } from 'app/shared/model/price-audit.model';
import { PriceAuditService } from './price-audit.service';
import { PriceAuditComponent } from './price-audit.component';
import { PriceAuditDetailComponent } from './price-audit-detail.component';

@Injectable({ providedIn: 'root' })
export class PriceAuditResolve implements Resolve<IPriceAudit> {
  constructor(private service: PriceAuditService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPriceAudit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((priceAudit: HttpResponse<PriceAudit>) => {
          if (priceAudit.body) {
            return of(priceAudit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PriceAudit());
  }
}

export const priceAuditRoute: Routes = [
  {
    path: '',
    component: PriceAuditComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PriceAudits'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PriceAuditDetailComponent,
    resolve: {
      priceAudit: PriceAuditResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PriceAudits'
    },
    canActivate: [UserRouteAccessService]
  }
];
