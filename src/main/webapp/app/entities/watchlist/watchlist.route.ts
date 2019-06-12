import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Watchlist } from 'app/shared/model/watchlist.model';
import { WatchlistService } from './watchlist.service';
import { WatchlistComponent } from './watchlist.component';
import { WatchlistDetailComponent } from './watchlist-detail.component';
import { WatchlistUpdateComponent } from './watchlist-update.component';
import { WatchlistDeletePopupComponent } from './watchlist-delete-dialog.component';
import { IWatchlist } from 'app/shared/model/watchlist.model';

@Injectable({ providedIn: 'root' })
export class WatchlistResolve implements Resolve<IWatchlist> {
  constructor(private service: WatchlistService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IWatchlist> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Watchlist>) => response.ok),
        map((watchlist: HttpResponse<Watchlist>) => watchlist.body)
      );
    }
    return of(new Watchlist());
  }
}

export const watchlistRoute: Routes = [
  {
    path: '',
    component: WatchlistComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'trackstockApp.watchlist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WatchlistDetailComponent,
    resolve: {
      watchlist: WatchlistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.watchlist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WatchlistUpdateComponent,
    resolve: {
      watchlist: WatchlistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.watchlist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WatchlistUpdateComponent,
    resolve: {
      watchlist: WatchlistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.watchlist.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const watchlistPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: WatchlistDeletePopupComponent,
    resolve: {
      watchlist: WatchlistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.watchlist.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
