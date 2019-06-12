/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InstrumentService } from 'app/entities/instrument/instrument.service';
import { IInstrument, Instrument, FinancialDataSources } from 'app/shared/model/instrument.model';

describe('Service Tests', () => {
  describe('Instrument Service', () => {
    let injector: TestBed;
    let service: InstrumentService;
    let httpMock: HttpTestingController;
    let elemDefault: IInstrument;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InstrumentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Instrument(
        0,
        FinancialDataSources.FXPRO,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateAdded: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT)
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

      it('should create a Instrument', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateAdded: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateAdded: currentDate,
            dateInactive: currentDate
          },
          returnedFromService
        );
        service
          .create(new Instrument(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Instrument', async () => {
        const returnedFromService = Object.assign(
          {
            dataProvider: 'BBBBBB',
            instrumentTicker: 'BBBBBB',
            instrumentExchnage: 'BBBBBB',
            instrumentDescription: 'BBBBBB',
            instrumentDataFrom: 'BBBBBB',
            instrumentActive: true,
            dateAdded: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAdded: currentDate,
            dateInactive: currentDate
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

      it('should return a list of Instrument', async () => {
        const returnedFromService = Object.assign(
          {
            dataProvider: 'BBBBBB',
            instrumentTicker: 'BBBBBB',
            instrumentExchnage: 'BBBBBB',
            instrumentDescription: 'BBBBBB',
            instrumentDataFrom: 'BBBBBB',
            instrumentActive: true,
            dateAdded: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateAdded: currentDate,
            dateInactive: currentDate
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

      it('should delete a Instrument', async () => {
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
