/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TrackstockTestModule } from '../../../test.module';
import { VideoPostUpdateComponent } from 'app/entities/video-post/video-post-update.component';
import { VideoPostService } from 'app/entities/video-post/video-post.service';
import { VideoPost } from 'app/shared/model/video-post.model';

describe('Component Tests', () => {
  describe('VideoPost Management Update Component', () => {
    let comp: VideoPostUpdateComponent;
    let fixture: ComponentFixture<VideoPostUpdateComponent>;
    let service: VideoPostService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [VideoPostUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VideoPostUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VideoPostUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VideoPostService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VideoPost(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new VideoPost();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
