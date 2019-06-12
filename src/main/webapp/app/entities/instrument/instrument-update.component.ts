import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IInstrument, Instrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from './instrument.service';
import { IPosition } from 'app/shared/model/position.model';
import { PositionService } from 'app/entities/position';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post';
import { IWatchlist } from 'app/shared/model/watchlist.model';
import { WatchlistService } from 'app/entities/watchlist';

@Component({
  selector: 'jhi-instrument-update',
  templateUrl: './instrument-update.component.html'
})
export class InstrumentUpdateComponent implements OnInit {
  instrument: IInstrument;
  isSaving: boolean;

  positions: IPosition[];

  posts: IPost[];

  watchlists: IWatchlist[];
  dateAddedDp: any;
  dateInactiveDp: any;

  editForm = this.fb.group({
    id: [],
    dataProvider: [],
    instrumentTicker: [null, [Validators.required]],
    instrumentExchnage: [],
    instrumentDescription: [],
    instrumentDataFrom: [],
    instrumentActive: [],
    dateAdded: [],
    dateInactive: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected instrumentService: InstrumentService,
    protected positionService: PositionService,
    protected postService: PostService,
    protected watchlistService: WatchlistService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ instrument }) => {
      this.updateForm(instrument);
      this.instrument = instrument;
    });
    this.positionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPosition[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPosition[]>) => response.body)
      )
      .subscribe((res: IPosition[]) => (this.positions = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.postService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPost[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPost[]>) => response.body)
      )
      .subscribe((res: IPost[]) => (this.posts = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.watchlistService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IWatchlist[]>) => mayBeOk.ok),
        map((response: HttpResponse<IWatchlist[]>) => response.body)
      )
      .subscribe((res: IWatchlist[]) => (this.watchlists = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(instrument: IInstrument) {
    this.editForm.patchValue({
      id: instrument.id,
      dataProvider: instrument.dataProvider,
      instrumentTicker: instrument.instrumentTicker,
      instrumentExchnage: instrument.instrumentExchnage,
      instrumentDescription: instrument.instrumentDescription,
      instrumentDataFrom: instrument.instrumentDataFrom,
      instrumentActive: instrument.instrumentActive,
      dateAdded: instrument.dateAdded,
      dateInactive: instrument.dateInactive
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const instrument = this.createFromForm();
    if (instrument.id !== undefined) {
      this.subscribeToSaveResponse(this.instrumentService.update(instrument));
    } else {
      this.subscribeToSaveResponse(this.instrumentService.create(instrument));
    }
  }

  private createFromForm(): IInstrument {
    const entity = {
      ...new Instrument(),
      id: this.editForm.get(['id']).value,
      dataProvider: this.editForm.get(['dataProvider']).value,
      instrumentTicker: this.editForm.get(['instrumentTicker']).value,
      instrumentExchnage: this.editForm.get(['instrumentExchnage']).value,
      instrumentDescription: this.editForm.get(['instrumentDescription']).value,
      instrumentDataFrom: this.editForm.get(['instrumentDataFrom']).value,
      instrumentActive: this.editForm.get(['instrumentActive']).value,
      dateAdded: this.editForm.get(['dateAdded']).value,
      dateInactive: this.editForm.get(['dateInactive']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstrument>>) {
    result.subscribe((res: HttpResponse<IInstrument>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPostById(index: number, item: IPost) {
    return item.id;
  }

  trackWatchlistById(index: number, item: IWatchlist) {
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
