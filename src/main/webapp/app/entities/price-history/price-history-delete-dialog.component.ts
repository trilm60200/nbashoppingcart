import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPriceHistory } from 'app/shared/model/price-history.model';
import { PriceHistoryService } from './price-history.service';

@Component({
  templateUrl: './price-history-delete-dialog.component.html'
})
export class PriceHistoryDeleteDialogComponent {
  priceHistory?: IPriceHistory;

  constructor(
    protected priceHistoryService: PriceHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.priceHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('priceHistoryListModification');
      this.activeModal.close();
    });
  }
}
