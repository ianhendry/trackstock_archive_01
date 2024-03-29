import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ShippingDetails } from 'app/shared/model/shipping-details.model';
import { ShippingDetailsService } from './shipping-details.service';
import { ShippingDetailsComponent } from './shipping-details.component';
import { ShippingDetailsDetailComponent } from './shipping-details-detail.component';
import { ShippingDetailsUpdateComponent } from './shipping-details-update.component';
import { ShippingDetailsDeletePopupComponent } from './shipping-details-delete-dialog.component';
import { IShippingDetails } from 'app/shared/model/shipping-details.model';

@Injectable({ providedIn: 'root' })
export class ShippingDetailsResolve implements Resolve<IShippingDetails> {
  constructor(private service: ShippingDetailsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IShippingDetails> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ShippingDetails>) => response.ok),
        map((shippingDetails: HttpResponse<ShippingDetails>) => shippingDetails.body)
      );
    }
    return of(new ShippingDetails());
  }
}

export const shippingDetailsRoute: Routes = [
  {
    path: '',
    component: ShippingDetailsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.shippingDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ShippingDetailsDetailComponent,
    resolve: {
      shippingDetails: ShippingDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.shippingDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ShippingDetailsUpdateComponent,
    resolve: {
      shippingDetails: ShippingDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.shippingDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ShippingDetailsUpdateComponent,
    resolve: {
      shippingDetails: ShippingDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.shippingDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const shippingDetailsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ShippingDetailsDeletePopupComponent,
    resolve: {
      shippingDetails: ShippingDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trackstockApp.shippingDetails.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
