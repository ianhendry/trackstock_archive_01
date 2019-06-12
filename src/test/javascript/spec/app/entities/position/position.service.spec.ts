/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PositionService } from 'app/entities/position/position.service';
import { IPosition, Position } from 'app/shared/model/position.model';

describe('Service Tests', () => {
  describe('Position Service', () => {
    let injector: TestBed;
    let service: PositionService;
    let httpMock: HttpTestingController;
    let elemDefault: IPosition;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PositionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Position(0, 'AAAAAAA', currentDate, 0, currentDate, 0, false, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            positionOpenDate: currentDate.format(DATE_FORMAT),
            positionCloseDate: currentDate.format(DATE_FORMAT)
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

      it('should create a Position', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            positionOpenDate: currentDate.format(DATE_FORMAT),
            positionCloseDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            positionOpenDate: currentDate,
            positionCloseDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Position(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Position', async () => {
        const returnedFromService = Object.assign(
          {
            positionTradePlan: 'BBBBBB',
            positionOpenDate: currentDate.format(DATE_FORMAT),
            positionOpenPrice: 1,
            positionCloseDate: currentDate.format(DATE_FORMAT),
            positionClosePrice: 1,
            positioWinLoss: true,
            positionProfitAmount: 1,
            positionClosingThought: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            positionOpenDate: currentDate,
            positionCloseDate: currentDate
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

      it('should return a list of Position', async () => {
        const returnedFromService = Object.assign(
          {
            positionTradePlan: 'BBBBBB',
            positionOpenDate: currentDate.format(DATE_FORMAT),
            positionOpenPrice: 1,
            positionCloseDate: currentDate.format(DATE_FORMAT),
            positionClosePrice: 1,
            positioWinLoss: true,
            positionProfitAmount: 1,
            positionClosingThought: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            positionOpenDate: currentDate,
            positionCloseDate: currentDate
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

      it('should delete a Position', async () => {
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
