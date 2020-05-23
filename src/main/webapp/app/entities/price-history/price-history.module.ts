import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NbaShoppingCartSharedModule } from 'app/shared/shared.module';
import { PriceHistoryComponent } from './price-history.component';
import { PriceHistoryDetailComponent } from './price-history-detail.component';
import { PriceHistoryUpdateComponent } from './price-history-update.component';
import { PriceHistoryDeleteDialogComponent } from './price-history-delete-dialog.component';
import { priceHistoryRoute } from './price-history.route';

@NgModule({
  imports: [NbaShoppingCartSharedModule, RouterModule.forChild(priceHistoryRoute)],
  declarations: [PriceHistoryComponent, PriceHistoryDetailComponent, PriceHistoryUpdateComponent, PriceHistoryDeleteDialogComponent],
  entryComponents: [PriceHistoryDeleteDialogComponent]
})
export class NbaShoppingCartPriceHistoryModule {}
