import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NbaShoppingCartTestModule } from '../../../test.module';
import { PriceAuditDetailComponent } from 'app/entities/price-audit/price-audit-detail.component';
import { PriceAudit } from 'app/shared/model/price-audit.model';

describe('Component Tests', () => {
  describe('PriceAudit Management Detail Component', () => {
    let comp: PriceAuditDetailComponent;
    let fixture: ComponentFixture<PriceAuditDetailComponent>;
    const route = ({ data: of({ priceAudit: new PriceAudit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NbaShoppingCartTestModule],
        declarations: [PriceAuditDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PriceAuditDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PriceAuditDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load priceAudit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.priceAudit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
