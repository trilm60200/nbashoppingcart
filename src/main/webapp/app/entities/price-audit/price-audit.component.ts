import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IPriceAudit } from 'app/shared/model/price-audit.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PriceAuditService } from './price-audit.service';

@Component({
  selector: 'jhi-price-audit',
  templateUrl: './price-audit.component.html'
})
export class PriceAuditComponent implements OnInit, OnDestroy {
  priceAudits: IPriceAudit[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected priceAuditService: PriceAuditService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks
  ) {
    this.priceAudits = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.priceAuditService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPriceAudit[]>) => this.paginatePriceAudits(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.priceAudits = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPriceAudits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPriceAudit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPriceAudits(): void {
    this.eventSubscriber = this.eventManager.subscribe('priceAuditListModification', () => this.reset());
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePriceAudits(data: IPriceAudit[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.priceAudits.push(data[i]);
      }
    }
  }
}
