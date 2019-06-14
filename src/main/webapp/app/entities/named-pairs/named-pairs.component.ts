import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INamedPairs } from 'app/shared/model/named-pairs.model';
import { AccountService } from 'app/core';
import { NamedPairsService } from './named-pairs.service';

@Component({
  selector: 'jhi-named-pairs',
  templateUrl: './named-pairs.component.html'
})
export class NamedPairsComponent implements OnInit, OnDestroy {
  namedPairs: INamedPairs[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected namedPairsService: NamedPairsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.namedPairsService
      .query()
      .pipe(
        filter((res: HttpResponse<INamedPairs[]>) => res.ok),
        map((res: HttpResponse<INamedPairs[]>) => res.body)
      )
      .subscribe(
        (res: INamedPairs[]) => {
          this.namedPairs = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInNamedPairs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INamedPairs) {
    return item.id;
  }

  registerChangeInNamedPairs() {
    this.eventSubscriber = this.eventManager.subscribe('namedPairsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
