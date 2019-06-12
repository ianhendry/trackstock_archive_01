import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPosition } from 'app/shared/model/position.model';

type EntityResponseType = HttpResponse<IPosition>;
type EntityArrayResponseType = HttpResponse<IPosition[]>;

@Injectable({ providedIn: 'root' })
export class PositionService {
  public resourceUrl = SERVER_API_URL + 'api/positions';

  constructor(protected http: HttpClient) {}

  create(position: IPosition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(position);
    return this.http
      .post<IPosition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(position: IPosition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(position);
    return this.http
      .put<IPosition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPosition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPosition[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(position: IPosition): IPosition {
    const copy: IPosition = Object.assign({}, position, {
      positionOpenDate:
        position.positionOpenDate != null && position.positionOpenDate.isValid() ? position.positionOpenDate.format(DATE_FORMAT) : null,
      positionCloseDate:
        position.positionCloseDate != null && position.positionCloseDate.isValid() ? position.positionCloseDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.positionOpenDate = res.body.positionOpenDate != null ? moment(res.body.positionOpenDate) : null;
      res.body.positionCloseDate = res.body.positionCloseDate != null ? moment(res.body.positionCloseDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((position: IPosition) => {
        position.positionOpenDate = position.positionOpenDate != null ? moment(position.positionOpenDate) : null;
        position.positionCloseDate = position.positionCloseDate != null ? moment(position.positionCloseDate) : null;
      });
    }
    return res;
  }
}
