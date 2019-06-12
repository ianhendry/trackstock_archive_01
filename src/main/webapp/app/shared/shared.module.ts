import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TrackstockSharedLibsModule, TrackstockSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [TrackstockSharedLibsModule, TrackstockSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [TrackstockSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrackstockSharedModule {
  static forRoot() {
    return {
      ngModule: TrackstockSharedModule
    };
  }
}
