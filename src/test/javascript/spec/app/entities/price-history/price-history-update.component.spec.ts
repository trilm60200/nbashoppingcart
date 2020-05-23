import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NbaShoppingCartTestModule } from '../../../test.module';
import { PriceHistoryUpdateComponent } from 'app/entities/price-history/price-history-update.component';
import { PriceHistoryService } from 'app/entities/price-history/price-history.service';
import { PriceHistory } from 'app/shared/model/price-history.model';

describe('Component Tests', () => {
  describe('PriceHistory Management Update Component', () => {
    let comp: PriceHistoryUpdateComponent;
    let fixture: ComponentFixture<PriceHistoryUpdateComponent>;
    let service: PriceHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NbaShoppingCartTestModule],
        declarations: [PriceHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PriceHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PriceHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PriceHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PriceHistory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PriceHistory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
