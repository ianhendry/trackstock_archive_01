/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrackstockTestModule } from '../../../test.module';
import { VideoPostDetailComponent } from 'app/entities/video-post/video-post-detail.component';
import { VideoPost } from 'app/shared/model/video-post.model';

describe('Component Tests', () => {
  describe('VideoPost Management Detail Component', () => {
    let comp: VideoPostDetailComponent;
    let fixture: ComponentFixture<VideoPostDetailComponent>;
    const route = ({ data: of({ videoPost: new VideoPost(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [VideoPostDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VideoPostDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VideoPostDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.videoPost).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
