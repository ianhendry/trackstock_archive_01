import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VideoPost } from 'app/shared/model/video-post.model';
import { VideoPostService } from './video-post.service';
import { VideoPostComponent } from './video-post.component';
import { VideoPostDetailComponent } from './video-post-detail.component';
import { VideoPostUpdateComponent } from './video-post-update.component';
import { VideoPostDeletePopupComponent } from './video-post-delete-dialog.component';
import { IVideoPost } from 'app/shared/model/video-post.model';

@Injectable({ providedIn: 'root' })
export class VideoPostResolve implements Resolve<IVideoPost> {
  constructor(private service: VideoPostService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVideoPost> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<VideoPost>) => response.ok),
        map((videoPost: HttpResponse<VideoPost>) => videoPost.body)
      );
    }
    return of(new VideoPost());
  }
}

export const videoPostRoute: Routes = [
  {
    path: '',
    component: VideoPostComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.videoPost.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VideoPostDetailComponent,
    resolve: {
      videoPost: VideoPostResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.videoPost.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VideoPostUpdateComponent,
    resolve: {
      videoPost: VideoPostResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.videoPost.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VideoPostUpdateComponent,
    resolve: {
      videoPost: VideoPostResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.videoPost.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const videoPostPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VideoPostDeletePopupComponent,
    resolve: {
      videoPost: VideoPostResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.videoPost.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
