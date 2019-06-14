import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TrackstockSharedModule } from 'app/shared';
import {
  NamedPairsComponent,
  NamedPairsDetailComponent,
  NamedPairsUpdateComponent,
  NamedPairsDeletePopupComponent,
  NamedPairsDeleteDialogComponent,
  namedPairsRoute,
  namedPairsPopupRoute
} from './';

const ENTITY_STATES = [...namedPairsRoute, ...namedPairsPopupRoute];

@NgModule({
  imports: [TrackstockSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NamedPairsComponent,
    NamedPairsDetailComponent,
    NamedPairsUpdateComponent,
    NamedPairsDeleteDialogComponent,
    NamedPairsDeletePopupComponent
  ],
  entryComponents: [NamedPairsComponent, NamedPairsUpdateComponent, NamedPairsDeleteDialogComponent, NamedPairsDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrackstockNamedPairsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
