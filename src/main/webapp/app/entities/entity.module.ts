import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.NbaShoppingCartProductModule)
      },
      {
        path: 'price-audit',
        loadChildren: () => import('./price-audit/price-audit.module').then(m => m.NbaShoppingCartPriceAuditModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class NbaShoppingCartEntityModule {}
