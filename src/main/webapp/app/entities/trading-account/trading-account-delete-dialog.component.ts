import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITradingAccount } from 'app/shared/model/trading-account.model';
import { TradingAccountService } from './trading-account.service';

@Component({
  selector: 'jhi-trading-account-delete-dialog',
  templateUrl: './trading-account-delete-dialog.component.html'
})
export class TradingAccountDeleteDialogComponent {
  tradingAccount: ITradingAccount;

  constructor(
    protected tradingAccountService: TradingAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tradingAccountService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tradingAccountListModification',
        content: 'Deleted an tradingAccount'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-trading-account-delete-popup',
  template: ''
})
export class TradingAccountDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tradingAccount }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TradingAccountDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tradingAccount = tradingAccount;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/trading-account', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/trading-account', { outlets: { popup: null } }]);
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
