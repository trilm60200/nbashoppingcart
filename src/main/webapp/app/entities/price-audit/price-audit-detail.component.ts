import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPriceAudit } from 'app/shared/model/price-audit.model';

@Component({
  selector: 'jhi-price-audit-detail',
  templateUrl: './price-audit-detail.component.html'
})
export class PriceAuditDetailComponent implements OnInit {
  priceAudit: IPriceAudit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priceAudit }) => (this.priceAudit = priceAudit));
  }

  previousState(): void {
    window.history.back();
  }
}
