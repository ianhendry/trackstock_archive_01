import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInstrument } from 'app/shared/model/instrument.model';

type EntityResponseType = HttpResponse<IInstrument>;
type EntityArrayResponseType = HttpResponse<IInstrument[]>;

@Injectable({ providedIn: 'root' })
export class InstrumentService {
  public resourceUrl = SERVER_API_URL + 'api/instruments';

  constructor(protected http: HttpClient) {}

  create(instrument: IInstrument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(instrument);
    return this.http
      .post<IInstrument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(instrument: IInstrument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(instrument);
    return this.http
      .put<IInstrument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInstrument>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInstrument[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(instrument: IInstrument): IInstrument {
    const copy: IInstrument = Object.assign({}, instrument, {
      dateAdded: instrument.dateAdded != null && instrument.dateAdded.isValid() ? instrument.dateAdded.format(DATE_FORMAT) : null,
      dateInactive:
        instrument.dateInactive != null && instrument.dateInactive.isValid() ? instrument.dateInactive.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAdded = res.body.dateAdded != null ? moment(res.body.dateAdded) : null;
      res.body.dateInactive = res.body.dateInactive != null ? moment(res.body.dateInactive) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((instrument: IInstrument) => {
        instrument.dateAdded = instrument.dateAdded != null ? moment(instrument.dateAdded) : null;
        instrument.dateInactive = instrument.dateInactive != null ? moment(instrument.dateInactive) : null;
      });
    }
    return res;
  }
}
