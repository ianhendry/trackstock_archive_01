import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITradingAccount } from 'app/shared/model/trading-account.model';
import { AccountService } from 'app/core';
import { TradingAccountService } from './trading-account.service';

@Component({
  selector: 'jhi-trading-account',
  templateUrl: './trading-account.component.html'
})
export class TradingAccountComponent implements OnInit, OnDestroy {
  tradingAccounts: ITradingAccount[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected tradingAccountService: TradingAccountService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.tradingAccountService
      .query()
      .pipe(
        filter((res: HttpResponse<ITradingAccount[]>) => res.ok),
        map((res: HttpResponse<ITradingAccount[]>) => res.body)
      )
      .subscribe(
        (res: ITradingAccount[]) => {
          this.tradingAccounts = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTradingAccounts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITradingAccount) {
    return item.id;
  }

  registerChangeInTradingAccounts() {
    this.eventSubscriber = this.eventManager.subscribe('tradingAccountListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
