import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPosition, Position } from 'app/shared/model/position.model';
import { PositionService } from './position.service';
import { IInstrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from 'app/entities/instrument';
import { ITradingAccount } from 'app/shared/model/trading-account.model';
import { TradingAccountService } from 'app/entities/trading-account';

@Component({
  selector: 'jhi-position-update',
  templateUrl: './position-update.component.html'
})
export class PositionUpdateComponent implements OnInit {
  position: IPosition;
  isSaving: boolean;

  instruments: IInstrument[];

  tradingaccounts: ITradingAccount[];
  positionOpenDateDp: any;
  positionCloseDateDp: any;

  editForm = this.fb.group({
    id: [],
    positionTradePlan: [null, [Validators.required]],
    positionOpenDate: [null, [Validators.required]],
    positionOpenPrice: [null, [Validators.required]],
    positionCloseDate: [],
    positionClosePrice: [],
    positioWinLoss: [],
    positionProfitAmount: [],
    positionClosingThought: [],
    instrument: [],
    tradingAccount: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected positionService: PositionService,
    protected instrumentService: InstrumentService,
    protected tradingAccountService: TradingAccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ position }) => {
      this.updateForm(position);
      this.position = position;
    });
    this.instrumentService
      .query({ filter: 'position-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IInstrument[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInstrument[]>) => response.body)
      )
      .subscribe(
        (res: IInstrument[]) => {
          if (!this.position.instrument || !this.position.instrument.id) {
            this.instruments = res;
          } else {
            this.instrumentService
              .find(this.position.instrument.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IInstrument>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IInstrument>) => subResponse.body)
              )
              .subscribe(
                (subRes: IInstrument) => (this.instruments = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.tradingAccountService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITradingAccount[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITradingAccount[]>) => response.body)
      )
      .subscribe((res: ITradingAccount[]) => (this.tradingaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(position: IPosition) {
    this.editForm.patchValue({
      id: position.id,
      positionTradePlan: position.positionTradePlan,
      positionOpenDate: position.positionOpenDate,
      positionOpenPrice: position.positionOpenPrice,
      positionCloseDate: position.positionCloseDate,
      positionClosePrice: position.positionClosePrice,
      positioWinLoss: position.positioWinLoss,
      positionProfitAmount: position.positionProfitAmount,
      positionClosingThought: position.positionClosingThought,
      instrument: position.instrument,
      tradingAccount: position.tradingAccount
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const position = this.createFromForm();
    if (position.id !== undefined) {
      this.subscribeToSaveResponse(this.positionService.update(position));
    } else {
      this.subscribeToSaveResponse(this.positionService.create(position));
    }
  }

  private createFromForm(): IPosition {
    const entity = {
      ...new Position(),
      id: this.editForm.get(['id']).value,
      positionTradePlan: this.editForm.get(['positionTradePlan']).value,
      positionOpenDate: this.editForm.get(['positionOpenDate']).value,
      positionOpenPrice: this.editForm.get(['positionOpenPrice']).value,
      positionCloseDate: this.editForm.get(['positionCloseDate']).value,
      positionClosePrice: this.editForm.get(['positionClosePrice']).value,
      positioWinLoss: this.editForm.get(['positioWinLoss']).value,
      positionProfitAmount: this.editForm.get(['positionProfitAmount']).value,
      positionClosingThought: this.editForm.get(['positionClosingThought']).value,
      instrument: this.editForm.get(['instrument']).value,
      tradingAccount: this.editForm.get(['tradingAccount']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPosition>>) {
    result.subscribe((res: HttpResponse<IPosition>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackInstrumentById(index: number, item: IInstrument) {
    return item.id;
  }

  trackTradingAccountById(index: number, item: ITradingAccount) {
    return item.id;
  }
}
