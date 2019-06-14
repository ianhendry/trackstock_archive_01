import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INamedPairs } from 'app/shared/model/named-pairs.model';
import { NamedPairsService } from './named-pairs.service';

@Component({
  selector: 'jhi-named-pairs-delete-dialog',
  templateUrl: './named-pairs-delete-dialog.component.html'
})
export class NamedPairsDeleteDialogComponent {
  namedPairs: INamedPairs;

  constructor(
    protected namedPairsService: NamedPairsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.namedPairsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'namedPairsListModification',
        content: 'Deleted an namedPairs'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-named-pairs-delete-popup',
  template: ''
})
export class NamedPairsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ namedPairs }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NamedPairsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.namedPairs = namedPairs;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/named-pairs', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/named-pairs', { outlets: { popup: null } }]);
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
