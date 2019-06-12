import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IShippingDetails } from 'app/shared/model/shipping-details.model';

@Component({
  selector: 'jhi-shipping-details-detail',
  templateUrl: './shipping-details-detail.component.html'
})
export class ShippingDetailsDetailComponent implements OnInit {
  shippingDetails: IShippingDetails;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shippingDetails }) => {
      this.shippingDetails = shippingDetails;
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
