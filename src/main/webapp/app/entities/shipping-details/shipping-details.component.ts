import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IShippingDetails } from 'app/shared/model/shipping-details.model';
import { AccountService } from 'app/core';
import { ShippingDetailsService } from './shipping-details.service';

@Component({
  selector: 'jhi-shipping-details',
  templateUrl: './shipping-details.component.html'
})
export class ShippingDetailsComponent implements OnInit, OnDestroy {
  shippingDetails: IShippingDetails[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected shippingDetailsService: ShippingDetailsService,
    protected jhiAlertService: JhiAlertService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.shippingDetailsService
      .query()
      .pipe(
        filter((res: HttpResponse<IShippingDetails[]>) => res.ok),
        map((res: HttpResponse<IShippingDetails[]>) => res.body)
      )
      .subscribe(
        (res: IShippingDetails[]) => {
          this.shippingDetails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInShippingDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IShippingDetails) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInShippingDetails() {
    this.eventSubscriber = this.eventManager.subscribe('shippingDetailsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
