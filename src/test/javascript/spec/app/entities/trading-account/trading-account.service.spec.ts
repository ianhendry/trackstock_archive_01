/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TradingAccountService } from 'app/entities/trading-account/trading-account.service';
import { ITradingAccount, TradingAccount } from 'app/shared/model/trading-account.model';

describe('Service Tests', () => {
  describe('TradingAccount Service', () => {
    let injector: TestBed;
    let service: TradingAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: ITradingAccount;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(TradingAccountService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TradingAccount(0, 'AAAAAAA', false, currentDate, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            accountOpenDate: currentDate.format(DATE_FORMAT),
            accountCloseDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a TradingAccount', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            accountOpenDate: currentDate.format(DATE_FORMAT),
            accountCloseDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            accountOpenDate: currentDate,
            accountCloseDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new TradingAccount(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a TradingAccount', async () => {
        const returnedFromService = Object.assign(
          {
            accountName: 'BBBBBB',
            accountReal: true,
            accountOpenDate: currentDate.format(DATE_FORMAT),
            accountBalance: 1,
            accountCloseDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            accountOpenDate: currentDate,
            accountCloseDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of TradingAccount', async () => {
        const returnedFromService = Object.assign(
          {
            accountName: 'BBBBBB',
            accountReal: true,
            accountOpenDate: currentDate.format(DATE_FORMAT),
            accountBalance: 1,
            accountCloseDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            accountOpenDate: currentDate,
            accountCloseDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TradingAccount', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
