import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IWatchlist, Watchlist } from 'app/shared/model/watchlist.model';
import { WatchlistService } from './watchlist.service';
import { IInstrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from 'app/entities/instrument';

@Component({
  selector: 'jhi-watchlist-update',
  templateUrl: './watchlist-update.component.html'
})
export class WatchlistUpdateComponent implements OnInit {
  watchlist: IWatchlist;
  isSaving: boolean;

  instruments: IInstrument[];
  dateCreatedDp: any;
  dateInactiveDp: any;

  editForm = this.fb.group({
    id: [],
    watchlistName: [null, [Validators.required]],
    watchlistDescription: [],
    dateCreated: [null, [Validators.required]],
    dateInactive: [],
    watchlistInactive: [],
    instruments: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected watchlistService: WatchlistService,
    protected instrumentService: InstrumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ watchlist }) => {
      this.updateForm(watchlist);
      this.watchlist = watchlist;
    });
    this.instrumentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInstrument[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInstrument[]>) => response.body)
      )
      .subscribe((res: IInstrument[]) => (this.instruments = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(watchlist: IWatchlist) {
    this.editForm.patchValue({
      id: watchlist.id,
      watchlistName: watchlist.watchlistName,
      watchlistDescription: watchlist.watchlistDescription,
      dateCreated: watchlist.dateCreated,
      dateInactive: watchlist.dateInactive,
      watchlistInactive: watchlist.watchlistInactive,
      instruments: watchlist.instruments
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const watchlist = this.createFromForm();
    if (watchlist.id !== undefined) {
      this.subscribeToSaveResponse(this.watchlistService.update(watchlist));
    } else {
      this.subscribeToSaveResponse(this.watchlistService.create(watchlist));
    }
  }

  private createFromForm(): IWatchlist {
    const entity = {
      ...new Watchlist(),
      id: this.editForm.get(['id']).value,
      watchlistName: this.editForm.get(['watchlistName']).value,
      watchlistDescription: this.editForm.get(['watchlistDescription']).value,
      dateCreated: this.editForm.get(['dateCreated']).value,
      dateInactive: this.editForm.get(['dateInactive']).value,
      watchlistInactive: this.editForm.get(['watchlistInactive']).value,
      instruments: this.editForm.get(['instruments']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWatchlist>>) {
    result.subscribe((res: HttpResponse<IWatchlist>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
