import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TrackstockSharedModule } from 'app/shared';
import {
  PositionComponent,
  PositionDetailComponent,
  PositionUpdateComponent,
  PositionDeletePopupComponent,
  PositionDeleteDialogComponent,
  positionRoute,
  positionPopupRoute
} from './';

const ENTITY_STATES = [...positionRoute, ...positionPopupRoute];

@NgModule({
  imports: [TrackstockSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PositionComponent,
    PositionDetailComponent,
    PositionUpdateComponent,
    PositionDeleteDialogComponent,
    PositionDeletePopupComponent
  ],
  entryComponents: [PositionComponent, PositionUpdateComponent, PositionDeleteDialogComponent, PositionDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrackstockPositionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
