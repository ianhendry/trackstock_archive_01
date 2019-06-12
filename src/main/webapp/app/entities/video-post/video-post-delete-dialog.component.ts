import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVideoPost } from 'app/shared/model/video-post.model';
import { VideoPostService } from './video-post.service';

@Component({
  selector: 'jhi-video-post-delete-dialog',
  templateUrl: './video-post-delete-dialog.component.html'
})
export class VideoPostDeleteDialogComponent {
  videoPost: IVideoPost;

  constructor(protected videoPostService: VideoPostService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.videoPostService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'videoPostListModification',
        content: 'Deleted an videoPost'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-video-post-delete-popup',
  template: ''
})
export class VideoPostDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ videoPost }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VideoPostDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.videoPost = videoPost;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/video-post', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/video-post', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
