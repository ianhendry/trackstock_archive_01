import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Instrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from './instrument.service';
import { InstrumentComponent } from './instrument.component';
import { InstrumentDetailComponent } from './instrument-detail.component';
import { InstrumentUpdateComponent } from './instrument-update.component';
import { InstrumentDeletePopupComponent } from './instrument-delete-dialog.component';
import { IInstrument } from 'app/shared/model/instrument.model';

@Injectable({ providedIn: 'root' })
export class InstrumentResolve implements Resolve<IInstrument> {
  constructor(private service: InstrumentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInstrument> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Instrument>) => response.ok),
        map((instrument: HttpResponse<Instrument>) => instrument.body)
      );
    }
    return of(new Instrument());
  }
}

export const instrumentRoute: Routes = [
  {
    path: '',
    component: InstrumentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'trackstockApp.instrument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InstrumentDetailComponent,
    resolve: {
      instrument: InstrumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.instrument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InstrumentUpdateComponent,
    resolve: {
      instrument: InstrumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.instrument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InstrumentUpdateComponent,
    resolve: {
      instrument: InstrumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.instrument.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const instrumentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InstrumentDeletePopupComponent,
    resolve: {
      instrument: InstrumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.instrument.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
