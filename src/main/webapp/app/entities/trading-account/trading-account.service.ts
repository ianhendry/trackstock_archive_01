import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITradingAccount } from 'app/shared/model/trading-account.model';

type EntityResponseType = HttpResponse<ITradingAccount>;
type EntityArrayResponseType = HttpResponse<ITradingAccount[]>;

@Injectable({ providedIn: 'root' })
export class TradingAccountService {
  public resourceUrl = SERVER_API_URL + 'api/trading-accounts';

  constructor(protected http: HttpClient) {}

  create(tradingAccount: ITradingAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tradingAccount);
    return this.http
      .post<ITradingAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tradingAccount: ITradingAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tradingAccount);
    return this.http
      .put<ITradingAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITradingAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITradingAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tradingAccount: ITradingAccount): ITradingAccount {
    const copy: ITradingAccount = Object.assign({}, tradingAccount, {
      accountOpenDate:
        tradingAccount.accountOpenDate != null && tradingAccount.accountOpenDate.isValid()
          ? tradingAccount.accountOpenDate.format(DATE_FORMAT)
          : null,
      accountCloseDate:
        tradingAccount.accountCloseDate != null && tradingAccount.accountCloseDate.isValid()
          ? tradingAccount.accountCloseDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.accountOpenDate = res.body.accountOpenDate != null ? moment(res.body.accountOpenDate) : null;
      res.body.accountCloseDate = res.body.accountCloseDate != null ? moment(res.body.accountCloseDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tradingAccount: ITradingAccount) => {
        tradingAccount.accountOpenDate = tradingAccount.accountOpenDate != null ? moment(tradingAccount.accountOpenDate) : null;
        tradingAccount.accountCloseDate = tradingAccount.accountCloseDate != null ? moment(tradingAccount.accountCloseDate) : null;
      });
    }
    return res;
  }
}
