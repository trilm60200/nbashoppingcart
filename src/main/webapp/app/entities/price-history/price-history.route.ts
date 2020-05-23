import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPriceHistory, PriceHistory } from 'app/shared/model/price-history.model';
import { PriceHistoryService } from './price-history.service';
import { PriceHistoryComponent } from './price-history.component';
import { PriceHistoryDetailComponent } from './price-history-detail.component';
import { PriceHistoryUpdateComponent } from './price-history-update.component';

@Injectable({ providedIn: 'root' })
export class PriceHistoryResolve implements Resolve<IPriceHistory> {
  constructor(private service: PriceHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPriceHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((priceHistory: HttpResponse<PriceHistory>) => {
          if (priceHistory.body) {
            return of(priceHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PriceHistory());
  }
}

export const priceHistoryRoute: Routes = [
  {
    path: '',
    component: PriceHistoryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PriceHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PriceHistoryDetailComponent,
    resolve: {
      priceHistory: PriceHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PriceHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PriceHistoryUpdateComponent,
    resolve: {
      priceHistory: PriceHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PriceHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PriceHistoryUpdateComponent,
    resolve: {
      priceHistory: PriceHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PriceHistories'
    },
    canActivate: [UserRouteAccessService]
  }
];
