import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IVideoPost } from 'app/shared/model/video-post.model';

@Component({
  selector: 'jhi-video-post-detail',
  templateUrl: './video-post-detail.component.html'
})
export class VideoPostDetailComponent implements OnInit {
  videoPost: IVideoPost;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ videoPost }) => {
      this.videoPost = videoPost;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
