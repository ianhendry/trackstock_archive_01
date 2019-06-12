/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TrackstockTestModule } from '../../../test.module';
import { VideoPostDeleteDialogComponent } from 'app/entities/video-post/video-post-delete-dialog.component';
import { VideoPostService } from 'app/entities/video-post/video-post.service';

describe('Component Tests', () => {
  describe('VideoPost Management Delete Component', () => {
    let comp: VideoPostDeleteDialogComponent;
    let fixture: ComponentFixture<VideoPostDeleteDialogComponent>;
    let service: VideoPostService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [VideoPostDeleteDialogComponent]
      })
        .overrideTemplate(VideoPostDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VideoPostDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VideoPostService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
