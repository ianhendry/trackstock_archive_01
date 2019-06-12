import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITradingAccount } from 'app/shared/model/trading-account.model';

@Component({
  selector: 'jhi-trading-account-detail',
  templateUrl: './trading-account-detail.component.html'
})
export class TradingAccountDetailComponent implements OnInit {
  tradingAccount: ITradingAccount;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tradingAccount }) => {
      this.tradingAccount = tradingAccount;
    });
  }

  previousState() {
    window.history.back();
  }
}
