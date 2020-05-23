import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPriceHistory } from 'app/shared/model/price-history.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PriceHistoryService } from './price-history.service';
import { PriceHistoryDeleteDialogComponent } from './price-history-delete-dialog.component';

@Component({
  selector: 'jhi-price-history',
  templateUrl: './price-history.component.html'
})
export class PriceHistoryComponent implements OnInit, OnDestroy {
  priceHistories: IPriceHistory[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected priceHistoryService: PriceHistoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.priceHistories = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.priceHistoryService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPriceHistory[]>) => this.paginatePriceHistories(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.priceHistories = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPriceHistories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPriceHistory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPriceHistories(): void {
    this.eventSubscriber = this.eventManager.subscribe('priceHistoryListModification', () => this.reset());
  }

  delete(priceHistory: IPriceHistory): void {
    const modalRef = this.modalService.open(PriceHistoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.priceHistory = priceHistory;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePriceHistories(data: IPriceHistory[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.priceHistories.push(data[i]);
      }
    }
  }
}
