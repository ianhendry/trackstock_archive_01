import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TrackstockSharedModule } from 'app/shared';
import {
  TradingAccountComponent,
  TradingAccountDetailComponent,
  TradingAccountUpdateComponent,
  TradingAccountDeletePopupComponent,
  TradingAccountDeleteDialogComponent,
  tradingAccountRoute,
  tradingAccountPopupRoute
} from './';

const ENTITY_STATES = [...tradingAccountRoute, ...tradingAccountPopupRoute];

@NgModule({
  imports: [TrackstockSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TradingAccountComponent,
    TradingAccountDetailComponent,
    TradingAccountUpdateComponent,
    TradingAccountDeleteDialogComponent,
    TradingAccountDeletePopupComponent
  ],
  entryComponents: [
    TradingAccountComponent,
    TradingAccountUpdateComponent,
    TradingAccountDeleteDialogComponent,
    TradingAccountDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrackstockTradingAccountModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
