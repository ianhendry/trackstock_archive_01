import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITradingAccount, TradingAccount } from 'app/shared/model/trading-account.model';
import { TradingAccountService } from './trading-account.service';
import { IPosition } from 'app/shared/model/position.model';
import { PositionService } from 'app/entities/position';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-trading-account-update',
  templateUrl: './trading-account-update.component.html'
})
export class TradingAccountUpdateComponent implements OnInit {
  tradingAccount: ITradingAccount;
  isSaving: boolean;

  positions: IPosition[];

  users: IUser[];
  accountOpenDateDp: any;
  accountCloseDateDp: any;

  editForm = this.fb.group({
    id: [],
    accountName: [null, [Validators.required]],
    accountReal: [null, [Validators.required]],
    accountOpenDate: [null, [Validators.required]],
    accountBalance: [null, [Validators.required]],
    accountCloseDate: [],
    position: [],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tradingAccountService: TradingAccountService,
    protected positionService: PositionService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tradingAccount }) => {
      this.updateForm(tradingAccount);
      this.tradingAccount = tradingAccount;
    });
    this.positionService
      .query({ filter: 'tradingaccount-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPosition[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPosition[]>) => response.body)
      )
      .subscribe(
        (res: IPosition[]) => {
          if (!this.tradingAccount.position || !this.tradingAccount.position.id) {
            this.positions = res;
          } else {
            this.positionService
              .find(this.tradingAccount.position.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPosition>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPosition>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPosition) => (this.positions = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tradingAccount: ITradingAccount) {
    this.editForm.patchValue({
      id: tradingAccount.id,
      accountName: tradingAccount.accountName,
      accountReal: tradingAccount.accountReal,
      accountOpenDate: tradingAccount.accountOpenDate,
      accountBalance: tradingAccount.accountBalance,
      accountCloseDate: tradingAccount.accountCloseDate,
      position: tradingAccount.position,
      user: tradingAccount.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tradingAccount = this.createFromForm();
    if (tradingAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.tradingAccountService.update(tradingAccount));
    } else {
      this.subscribeToSaveResponse(this.tradingAccountService.create(tradingAccount));
    }
  }

  private createFromForm(): ITradingAccount {
    const entity = {
      ...new TradingAccount(),
      id: this.editForm.get(['id']).value,
      accountName: this.editForm.get(['accountName']).value,
      accountReal: this.editForm.get(['accountReal']).value,
      accountOpenDate: this.editForm.get(['accountOpenDate']).value,
      accountBalance: this.editForm.get(['accountBalance']).value,
      accountCloseDate: this.editForm.get(['accountCloseDate']).value,
      position: this.editForm.get(['position']).value,
      user: this.editForm.get(['user']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITradingAccount>>) {
    result.subscribe((res: HttpResponse<ITradingAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPositionById(index: number, item: IPosition) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
