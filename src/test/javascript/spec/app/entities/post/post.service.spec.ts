/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PostService } from 'app/entities/post/post.service';
import { IPost, Post } from 'app/shared/model/post.model';

describe('Service Tests', () => {
  describe('Post Service', () => {
    let injector: TestBed;
    let service: PostService;
    let httpMock: HttpTestingController;
    let elemDefault: IPost;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PostService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Post(
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateAdded: currentDate.format(DATE_FORMAT),
            dateApproved: currentDate.format(DATE_FORMAT)
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

      it('should create a Post', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateAdded: currentDate.format(DATE_FORMAT),
            dateApproved: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateAdded: currentDate,
            dateApproved: currentDate
          },
          returnedFromService
        );
        service
          .create(new Post(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Post', async () => {
        const returnedFromService = Object.assign(
          {
            postTitle: 'BBBBBB',
            postBody: 'BBBBBB',
            dateAdded: currentDate.format(DATE_FORMAT),
            dateApproved: currentDate.format(DATE_FORMAT),
            media1: 'BBBBBB',
            media2: 'BBBBBB',
            media3: 'BBBBBB',
            media4: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAdded: currentDate,
            dateApproved: currentDate
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

      it('should return a list of Post', async () => {
        const returnedFromService = Object.assign(
          {
            postTitle: 'BBBBBB',
            postBody: 'BBBBBB',
            dateAdded: currentDate.format(DATE_FORMAT),
            dateApproved: currentDate.format(DATE_FORMAT),
            media1: 'BBBBBB',
            media2: 'BBBBBB',
            media3: 'BBBBBB',
            media4: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateAdded: currentDate,
            dateApproved: currentDate
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

      it('should delete a Post', async () => {
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
