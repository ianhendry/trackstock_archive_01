import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVideoPost } from 'app/shared/model/video-post.model';

type EntityResponseType = HttpResponse<IVideoPost>;
type EntityArrayResponseType = HttpResponse<IVideoPost[]>;

@Injectable({ providedIn: 'root' })
export class VideoPostService {
  public resourceUrl = SERVER_API_URL + 'api/video-posts';

  constructor(protected http: HttpClient) {}

  create(videoPost: IVideoPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(videoPost);
    return this.http
      .post<IVideoPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(videoPost: IVideoPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(videoPost);
    return this.http
      .put<IVideoPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVideoPost>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVideoPost[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(videoPost: IVideoPost): IVideoPost {
    const copy: IVideoPost = Object.assign({}, videoPost, {
      dateAdded: videoPost.dateAdded != null && videoPost.dateAdded.isValid() ? videoPost.dateAdded.format(DATE_FORMAT) : null,
      dateApproved: videoPost.dateApproved != null && videoPost.dateApproved.isValid() ? videoPost.dateApproved.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAdded = res.body.dateAdded != null ? moment(res.body.dateAdded) : null;
      res.body.dateApproved = res.body.dateApproved != null ? moment(res.body.dateApproved) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((videoPost: IVideoPost) => {
        videoPost.dateAdded = videoPost.dateAdded != null ? moment(videoPost.dateAdded) : null;
        videoPost.dateApproved = videoPost.dateApproved != null ? moment(videoPost.dateApproved) : null;
      });
    }
    return res;
  }
}
