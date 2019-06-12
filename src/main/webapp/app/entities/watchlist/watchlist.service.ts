import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWatchlist } from 'app/shared/model/watchlist.model';

type EntityResponseType = HttpResponse<IWatchlist>;
type EntityArrayResponseType = HttpResponse<IWatchlist[]>;

@Injectable({ providedIn: 'root' })
export class WatchlistService {
  public resourceUrl = SERVER_API_URL + 'api/watchlists';

  constructor(protected http: HttpClient) {}

  create(watchlist: IWatchlist): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(watchlist);
    return this.http
      .post<IWatchlist>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(watchlist: IWatchlist): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(watchlist);
    return this.http
      .put<IWatchlist>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWatchlist>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWatchlist[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(watchlist: IWatchlist): IWatchlist {
    const copy: IWatchlist = Object.assign({}, watchlist, {
      dateCreated: watchlist.dateCreated != null && watchlist.dateCreated.isValid() ? watchlist.dateCreated.format(DATE_FORMAT) : null,
      dateInactive: watchlist.dateInactive != null && watchlist.dateInactive.isValid() ? watchlist.dateInactive.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
      res.body.dateInactive = res.body.dateInactive != null ? moment(res.body.dateInactive) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((watchlist: IWatchlist) => {
        watchlist.dateCreated = watchlist.dateCreated != null ? moment(watchlist.dateCreated) : null;
        watchlist.dateInactive = watchlist.dateInactive != null ? moment(watchlist.dateInactive) : null;
      });
    }
    return res;
  }
}
