import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TradingAccount } from 'app/shared/model/trading-account.model';
import { TradingAccountService } from './trading-account.service';
import { TradingAccountComponent } from './trading-account.component';
import { TradingAccountDetailComponent } from './trading-account-detail.component';
import { TradingAccountUpdateComponent } from './trading-account-update.component';
import { TradingAccountDeletePopupComponent } from './trading-account-delete-dialog.component';
import { ITradingAccount } from 'app/shared/model/trading-account.model';

@Injectable({ providedIn: 'root' })
export class TradingAccountResolve implements Resolve<ITradingAccount> {
  constructor(private service: TradingAccountService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITradingAccount> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TradingAccount>) => response.ok),
        map((tradingAccount: HttpResponse<TradingAccount>) => tradingAccount.body)
      );
    }
    return of(new TradingAccount());
  }
}

export const tradingAccountRoute: Routes = [
  {
    path: '',
    component: TradingAccountComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.tradingAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TradingAccountDetailComponent,
    resolve: {
      tradingAccount: TradingAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.tradingAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TradingAccountUpdateComponent,
    resolve: {
      tradingAccount: TradingAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.tradingAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TradingAccountUpdateComponent,
    resolve: {
      tradingAccount: TradingAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.tradingAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tradingAccountPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TradingAccountDeletePopupComponent,
    resolve: {
      tradingAccount: TradingAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.tradingAccount.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
