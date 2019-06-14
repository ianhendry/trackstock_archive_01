import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INamedPairs } from 'app/shared/model/named-pairs.model';

type EntityResponseType = HttpResponse<INamedPairs>;
type EntityArrayResponseType = HttpResponse<INamedPairs[]>;

@Injectable({ providedIn: 'root' })
export class NamedPairsService {
  public resourceUrl = SERVER_API_URL + 'api/named-pairs';

  constructor(protected http: HttpClient) {}

  create(namedPairs: INamedPairs): Observable<EntityResponseType> {
    return this.http.post<INamedPairs>(this.resourceUrl, namedPairs, { observe: 'response' });
  }

  update(namedPairs: INamedPairs): Observable<EntityResponseType> {
    return this.http.put<INamedPairs>(this.resourceUrl, namedPairs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INamedPairs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INamedPairs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
