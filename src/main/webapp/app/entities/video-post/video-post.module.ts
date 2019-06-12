import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TrackstockSharedModule } from 'app/shared';
import {
  VideoPostComponent,
  VideoPostDetailComponent,
  VideoPostUpdateComponent,
  VideoPostDeletePopupComponent,
  VideoPostDeleteDialogComponent,
  videoPostRoute,
  videoPostPopupRoute
} from './';

const ENTITY_STATES = [...videoPostRoute, ...videoPostPopupRoute];

@NgModule({
  imports: [TrackstockSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VideoPostComponent,
    VideoPostDetailComponent,
    VideoPostUpdateComponent,
    VideoPostDeleteDialogComponent,
    VideoPostDeletePopupComponent
  ],
  entryComponents: [VideoPostComponent, VideoPostUpdateComponent, VideoPostDeleteDialogComponent, VideoPostDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrackstockVideoPostModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
