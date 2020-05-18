import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { NbaShoppingCartSharedModule } from 'app/shared/shared.module';
import { NbaShoppingCartCoreModule } from 'app/core/core.module';
import { NbaShoppingCartAppRoutingModule } from './app-routing.module';
import { NbaShoppingCartHomeModule } from './home/home.module';
import { NbaShoppingCartEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    NbaShoppingCartSharedModule,
    NbaShoppingCartCoreModule,
    NbaShoppingCartHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    NbaShoppingCartEntityModule,
    NbaShoppingCartAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class NbaShoppingCartAppModule {}
