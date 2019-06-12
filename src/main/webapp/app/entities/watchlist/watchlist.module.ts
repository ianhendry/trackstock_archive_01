import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TrackstockSharedModule } from 'app/shared';
import {
  WatchlistComponent,
  WatchlistDetailComponent,
  WatchlistUpdateComponent,
  WatchlistDeletePopupComponent,
  WatchlistDeleteDialogComponent,
  watchlistRoute,
  watchlistPopupRoute
} from './';

const ENTITY_STATES = [...watchlistRoute, ...watchlistPopupRoute];

@NgModule({
  imports: [TrackstockSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    WatchlistComponent,
    WatchlistDetailComponent,
    WatchlistUpdateComponent,
    WatchlistDeleteDialogComponent,
    WatchlistDeletePopupComponent
  ],
  entryComponents: [WatchlistComponent, WatchlistUpdateComponent, WatchlistDeleteDialogComponent, WatchlistDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrackstockWatchlistModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
