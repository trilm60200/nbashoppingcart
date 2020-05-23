import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPriceHistory, PriceHistory } from 'app/shared/model/price-history.model';
import { PriceHistoryService } from './price-history.service';

@Component({
  selector: 'jhi-price-history-update',
  templateUrl: './price-history-update.component.html'
})
export class PriceHistoryUpdateComponent implements OnInit {
  isSaving = false;
  created_dateDp: any;

  editForm = this.fb.group({
    id: [],
    product_id: [],
    price: [],
    created_date: []
  });

  constructor(protected priceHistoryService: PriceHistoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priceHistory }) => {
      this.updateForm(priceHistory);
    });
  }

  updateForm(priceHistory: IPriceHistory): void {
    this.editForm.patchValue({
      id: priceHistory.id,
      product_id: priceHistory.product_id,
      price: priceHistory.price,
      created_date: priceHistory.created_date
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const priceHistory = this.createFromForm();
    if (priceHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.priceHistoryService.update(priceHistory));
    } else {
      this.subscribeToSaveResponse(this.priceHistoryService.create(priceHistory));
    }
  }

  private createFromForm(): IPriceHistory {
    return {
      ...new PriceHistory(),
      id: this.editForm.get(['id'])!.value,
      product_id: this.editForm.get(['product_id'])!.value,
      price: this.editForm.get(['price'])!.value,
      created_date: this.editForm.get(['created_date'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPriceHistory>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
