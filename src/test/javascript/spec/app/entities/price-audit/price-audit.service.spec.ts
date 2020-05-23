import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PriceAuditService } from 'app/entities/price-audit/price-audit.service';
import { IPriceAudit, PriceAudit } from 'app/shared/model/price-audit.model';

describe('Service Tests', () => {
  describe('PriceAudit Service', () => {
    let injector: TestBed;
    let service: PriceAuditService;
    let httpMock: HttpTestingController;
    let elemDefault: IPriceAudit;
    let expectedResult: IPriceAudit | IPriceAudit[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PriceAuditService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PriceAudit(0, 0, 'AAAAAAA', 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should return a list of PriceAudit', () => {
        const returnedFromService = Object.assign(
          {
            productId: 1,
            productName: 'BBBBBB',
            price: 1,
            createDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
