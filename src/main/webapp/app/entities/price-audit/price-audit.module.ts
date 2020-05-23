import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NbaShoppingCartSharedModule } from 'app/shared/shared.module';
import { PriceAuditComponent } from './price-audit.component';
import { PriceAuditDetailComponent } from './price-audit-detail.component';
import { priceAuditRoute } from './price-audit.route';

@NgModule({
  imports: [NbaShoppingCartSharedModule, RouterModule.forChild(priceAuditRoute)],
  declarations: [PriceAuditComponent, PriceAuditDetailComponent]
})
export class NbaShoppingCartPriceAuditModule {}
