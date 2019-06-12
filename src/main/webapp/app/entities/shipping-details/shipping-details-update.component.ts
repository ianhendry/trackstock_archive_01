import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IShippingDetails, ShippingDetails } from 'app/shared/model/shipping-details.model';
import { ShippingDetailsService } from './shipping-details.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-shipping-details-update',
  templateUrl: './shipping-details-update.component.html'
})
export class ShippingDetailsUpdateComponent implements OnInit {
  shippingDetails: IShippingDetails;
  isSaving: boolean;

  users: IUser[];
  dateAddedDp: any;
  dateInactiveDp: any;

  editForm = this.fb.group({
    id: [],
    userName: [],
    email: [],
    name: [],
    address1: [],
    address2: [],
    address3: [],
    address4: [],
    address5: [],
    dateAdded: [],
    dateInactive: [],
    userPicture: [],
    userPictureContentType: [],
    user: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected shippingDetailsService: ShippingDetailsService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ shippingDetails }) => {
      this.updateForm(shippingDetails);
      this.shippingDetails = shippingDetails;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(shippingDetails: IShippingDetails) {
    this.editForm.patchValue({
      id: shippingDetails.id,
      userName: shippingDetails.userName,
      email: shippingDetails.email,
      name: shippingDetails.name,
      address1: shippingDetails.address1,
      address2: shippingDetails.address2,
      address3: shippingDetails.address3,
      address4: shippingDetails.address4,
      address5: shippingDetails.address5,
      dateAdded: shippingDetails.dateAdded,
      dateInactive: shippingDetails.dateInactive,
      userPicture: shippingDetails.userPicture,
      userPictureContentType: shippingDetails.userPictureContentType,
      user: shippingDetails.user
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const shippingDetails = this.createFromForm();
    if (shippingDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.shippingDetailsService.update(shippingDetails));
    } else {
      this.subscribeToSaveResponse(this.shippingDetailsService.create(shippingDetails));
    }
  }

  private createFromForm(): IShippingDetails {
    const entity = {
      ...new ShippingDetails(),
      id: this.editForm.get(['id']).value,
      userName: this.editForm.get(['userName']).value,
      email: this.editForm.get(['email']).value,
      name: this.editForm.get(['name']).value,
      address1: this.editForm.get(['address1']).value,
      address2: this.editForm.get(['address2']).value,
      address3: this.editForm.get(['address3']).value,
      address4: this.editForm.get(['address4']).value,
      address5: this.editForm.get(['address5']).value,
      dateAdded: this.editForm.get(['dateAdded']).value,
      dateInactive: this.editForm.get(['dateInactive']).value,
      userPictureContentType: this.editForm.get(['userPictureContentType']).value,
      userPicture: this.editForm.get(['userPicture']).value,
      user: this.editForm.get(['user']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShippingDetails>>) {
    result.subscribe((res: HttpResponse<IShippingDetails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
