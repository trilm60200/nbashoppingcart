import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NbaShoppingCartTestModule } from '../../../test.module';
import { PriceHistoryDetailComponent } from 'app/entities/price-history/price-history-detail.component';
import { PriceHistory } from 'app/shared/model/price-history.model';

describe('Component Tests', () => {
  describe('PriceHistory Management Detail Component', () => {
    let comp: PriceHistoryDetailComponent;
    let fixture: ComponentFixture<PriceHistoryDetailComponent>;
    const route = ({ data: of({ priceHistory: new PriceHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NbaShoppingCartTestModule],
        declarations: [PriceHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PriceHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PriceHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load priceHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.priceHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
