import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShippingDetails } from 'app/shared/model/shipping-details.model';

type EntityResponseType = HttpResponse<IShippingDetails>;
type EntityArrayResponseType = HttpResponse<IShippingDetails[]>;

@Injectable({ providedIn: 'root' })
export class ShippingDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/shipping-details';

  constructor(protected http: HttpClient) {}

  create(shippingDetails: IShippingDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shippingDetails);
    return this.http
      .post<IShippingDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(shippingDetails: IShippingDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shippingDetails);
    return this.http
      .put<IShippingDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IShippingDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IShippingDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(shippingDetails: IShippingDetails): IShippingDetails {
    const copy: IShippingDetails = Object.assign({}, shippingDetails, {
      dateAdded:
        shippingDetails.dateAdded != null && shippingDetails.dateAdded.isValid() ? shippingDetails.dateAdded.format(DATE_FORMAT) : null,
      dateInactive:
        shippingDetails.dateInactive != null && shippingDetails.dateInactive.isValid()
          ? shippingDetails.dateInactive.format(DATE_FORMAT)
          : null
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
      res.body.forEach((shippingDetails: IShippingDetails) => {
        shippingDetails.dateAdded = shippingDetails.dateAdded != null ? moment(shippingDetails.dateAdded) : null;
        shippingDetails.dateInactive = shippingDetails.dateInactive != null ? moment(shippingDetails.dateInactive) : null;
      });
    }
    return res;
  }
}
