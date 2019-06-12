import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TrackstockSharedModule } from 'app/shared';
import {
  InstrumentComponent,
  InstrumentDetailComponent,
  InstrumentUpdateComponent,
  InstrumentDeletePopupComponent,
  InstrumentDeleteDialogComponent,
  instrumentRoute,
  instrumentPopupRoute
} from './';

const ENTITY_STATES = [...instrumentRoute, ...instrumentPopupRoute];

@NgModule({
  imports: [TrackstockSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InstrumentComponent,
    InstrumentDetailComponent,
    InstrumentUpdateComponent,
    InstrumentDeleteDialogComponent,
    InstrumentDeletePopupComponent
  ],
  entryComponents: [InstrumentComponent, InstrumentUpdateComponent, InstrumentDeleteDialogComponent, InstrumentDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrackstockInstrumentModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
