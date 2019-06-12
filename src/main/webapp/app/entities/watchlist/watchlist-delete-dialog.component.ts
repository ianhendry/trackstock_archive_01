import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWatchlist } from 'app/shared/model/watchlist.model';
import { WatchlistService } from './watchlist.service';

@Component({
  selector: 'jhi-watchlist-delete-dialog',
  templateUrl: './watchlist-delete-dialog.component.html'
})
export class WatchlistDeleteDialogComponent {
  watchlist: IWatchlist;

  constructor(protected watchlistService: WatchlistService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.watchlistService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'watchlistListModification',
        content: 'Deleted an watchlist'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-watchlist-delete-popup',
  template: ''
})
export class WatchlistDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ watchlist }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(WatchlistDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.watchlist = watchlist;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/watchlist', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/watchlist', { outlets: { popup: null } }]);
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
