import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NamedPairs } from 'app/shared/model/named-pairs.model';
import { NamedPairsService } from './named-pairs.service';
import { NamedPairsComponent } from './named-pairs.component';
import { NamedPairsDetailComponent } from './named-pairs-detail.component';
import { NamedPairsUpdateComponent } from './named-pairs-update.component';
import { NamedPairsDeletePopupComponent } from './named-pairs-delete-dialog.component';
import { INamedPairs } from 'app/shared/model/named-pairs.model';

@Injectable({ providedIn: 'root' })
export class NamedPairsResolve implements Resolve<INamedPairs> {
  constructor(private service: NamedPairsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INamedPairs> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<NamedPairs>) => response.ok),
        map((namedPairs: HttpResponse<NamedPairs>) => namedPairs.body)
      );
    }
    return of(new NamedPairs());
  }
}

export const namedPairsRoute: Routes = [
  {
    path: '',
    component: NamedPairsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.namedPairs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NamedPairsDetailComponent,
    resolve: {
      namedPairs: NamedPairsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.namedPairs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NamedPairsUpdateComponent,
    resolve: {
      namedPairs: NamedPairsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.namedPairs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NamedPairsUpdateComponent,
    resolve: {
      namedPairs: NamedPairsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.namedPairs.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const namedPairsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NamedPairsDeletePopupComponent,
    resolve: {
      namedPairs: NamedPairsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.namedPairs.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
