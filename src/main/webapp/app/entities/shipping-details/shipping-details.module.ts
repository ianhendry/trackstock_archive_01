import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TrackstockSharedModule } from 'app/shared';
import {
  ShippingDetailsComponent,
  ShippingDetailsDetailComponent,
  ShippingDetailsUpdateComponent,
  ShippingDetailsDeletePopupComponent,
  ShippingDetailsDeleteDialogComponent,
  shippingDetailsRoute,
  shippingDetailsPopupRoute
} from './';

const ENTITY_STATES = [...shippingDetailsRoute, ...shippingDetailsPopupRoute];

@NgModule({
  imports: [TrackstockSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ShippingDetailsComponent,
    ShippingDetailsDetailComponent,
    ShippingDetailsUpdateComponent,
    ShippingDetailsDeleteDialogComponent,
    ShippingDetailsDeletePopupComponent
  ],
  entryComponents: [
    ShippingDetailsComponent,
    ShippingDetailsUpdateComponent,
    ShippingDetailsDeleteDialogComponent,
    ShippingDetailsDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrackstockShippingDetailsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
