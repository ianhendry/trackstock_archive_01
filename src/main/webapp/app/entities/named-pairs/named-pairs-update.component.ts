import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INamedPairs, NamedPairs } from 'app/shared/model/named-pairs.model';
import { NamedPairsService } from './named-pairs.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-named-pairs-update',
  templateUrl: './named-pairs-update.component.html'
})
export class NamedPairsUpdateComponent implements OnInit {
  namedPairs: INamedPairs;
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    groupName: [null, [Validators.required]],
    key: [null, [Validators.required]],
    value: [null, [Validators.required]],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected namedPairsService: NamedPairsService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ namedPairs }) => {
      this.updateForm(namedPairs);
      this.namedPairs = namedPairs;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(namedPairs: INamedPairs) {
    this.editForm.patchValue({
      id: namedPairs.id,
      groupName: namedPairs.groupName,
      key: namedPairs.key,
      value: namedPairs.value,
      user: namedPairs.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const namedPairs = this.createFromForm();
    if (namedPairs.id !== undefined) {
      this.subscribeToSaveResponse(this.namedPairsService.update(namedPairs));
    } else {
      this.subscribeToSaveResponse(this.namedPairsService.create(namedPairs));
    }
  }

  private createFromForm(): INamedPairs {
    const entity = {
      ...new NamedPairs(),
      id: this.editForm.get(['id']).value,
      groupName: this.editForm.get(['groupName']).value,
      key: this.editForm.get(['key']).value,
      value: this.editForm.get(['value']).value,
      user: this.editForm.get(['user']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INamedPairs>>) {
    result.subscribe((res: HttpResponse<INamedPairs>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
