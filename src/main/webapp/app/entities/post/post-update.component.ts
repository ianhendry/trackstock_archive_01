import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPost, Post } from 'app/shared/model/post.model';
import { PostService } from './post.service';
import { IInstrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from 'app/entities/instrument';
import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from 'app/entities/comment';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-post-update',
  templateUrl: './post-update.component.html'
})
export class PostUpdateComponent implements OnInit {
  post: IPost;
  isSaving: boolean;

  instruments: IInstrument[];

  comments: IComment[];

  users: IUser[];
  dateAddedDp: any;
  dateApprovedDp: any;

  editForm = this.fb.group({
    id: [],
    postTitle: [null, [Validators.required]],
    postBody: [],
    dateAdded: [null, [Validators.required]],
    dateApproved: [],
    media1: [],
    media1ContentType: [],
    media2: [],
    media2ContentType: [],
    media3: [],
    media3ContentType: [],
    media4: [],
    media4ContentType: [],
    instrument: [],
    comment: [],
    user: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected postService: PostService,
    protected instrumentService: InstrumentService,
    protected commentService: CommentService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ post }) => {
      this.updateForm(post);
      this.post = post;
    });
    this.instrumentService
      .query({ filter: 'post-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IInstrument[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInstrument[]>) => response.body)
      )
      .subscribe(
        (res: IInstrument[]) => {
          if (!this.post.instrument || !this.post.instrument.id) {
            this.instruments = res;
          } else {
            this.instrumentService
              .find(this.post.instrument.id)
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
    this.commentService
      .query({ filter: 'post-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IComment[]>) => mayBeOk.ok),
        map((response: HttpResponse<IComment[]>) => response.body)
      )
      .subscribe(
        (res: IComment[]) => {
          if (!this.post.comment || !this.post.comment.id) {
            this.comments = res;
          } else {
            this.commentService
              .find(this.post.comment.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IComment>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IComment>) => subResponse.body)
              )
              .subscribe(
                (subRes: IComment) => (this.comments = [subRes].concat(res)),
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

  updateForm(post: IPost) {
    this.editForm.patchValue({
      id: post.id,
      postTitle: post.postTitle,
      postBody: post.postBody,
      dateAdded: post.dateAdded,
      dateApproved: post.dateApproved,
      media1: post.media1,
      media1ContentType: post.media1ContentType,
      media2: post.media2,
      media2ContentType: post.media2ContentType,
      media3: post.media3,
      media3ContentType: post.media3ContentType,
      media4: post.media4,
      media4ContentType: post.media4ContentType,
      instrument: post.instrument,
      comment: post.comment,
      user: post.user
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
    const post = this.createFromForm();
    if (post.id !== undefined) {
      this.subscribeToSaveResponse(this.postService.update(post));
    } else {
      this.subscribeToSaveResponse(this.postService.create(post));
    }
  }

  private createFromForm(): IPost {
    const entity = {
      ...new Post(),
      id: this.editForm.get(['id']).value,
      postTitle: this.editForm.get(['postTitle']).value,
      postBody: this.editForm.get(['postBody']).value,
      dateAdded: this.editForm.get(['dateAdded']).value,
      dateApproved: this.editForm.get(['dateApproved']).value,
      media1ContentType: this.editForm.get(['media1ContentType']).value,
      media1: this.editForm.get(['media1']).value,
      media2ContentType: this.editForm.get(['media2ContentType']).value,
      media2: this.editForm.get(['media2']).value,
      media3ContentType: this.editForm.get(['media3ContentType']).value,
      media3: this.editForm.get(['media3']).value,
      media4ContentType: this.editForm.get(['media4ContentType']).value,
      media4: this.editForm.get(['media4']).value,
      instrument: this.editForm.get(['instrument']).value,
      comment: this.editForm.get(['comment']).value,
      user: this.editForm.get(['user']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPost>>) {
    result.subscribe((res: HttpResponse<IPost>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCommentById(index: number, item: IComment) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
