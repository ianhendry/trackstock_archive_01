import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IComment, Comment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { IUser, UserService } from 'app/core';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post';
import { IWatchlist } from 'app/shared/model/watchlist.model';
import { WatchlistService } from 'app/entities/watchlist';

@Component({
  selector: 'jhi-comment-update',
  templateUrl: './comment-update.component.html'
})
export class CommentUpdateComponent implements OnInit {
  comment: IComment;
  isSaving: boolean;

  replies: IComment[];

  users: IUser[];

  posts: IPost[];

  watchlists: IWatchlist[];
  dateAddedDp: any;
  dateApprovedDp: any;

  editForm = this.fb.group({
    id: [],
    commentTitle: [null, [Validators.required]],
    commentBody: [],
    commentMedia: [],
    commentMediaContentType: [],
    dateAdded: [null, [Validators.required]],
    dateApproved: [],
    reply: [],
    user: [],
    watchlist: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected commentService: CommentService,
    protected userService: UserService,
    protected postService: PostService,
    protected watchlistService: WatchlistService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ comment }) => {
      this.updateForm(comment);
      this.comment = comment;
    });
    this.commentService
      .query({ filter: 'comment-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IComment[]>) => mayBeOk.ok),
        map((response: HttpResponse<IComment[]>) => response.body)
      )
      .subscribe(
        (res: IComment[]) => {
          if (!this.comment.reply || !this.comment.reply.id) {
            this.replies = res;
          } else {
            this.commentService
              .find(this.comment.reply.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IComment>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IComment>) => subResponse.body)
              )
              .subscribe(
                (subRes: IComment) => (this.replies = [subRes].concat(res)),
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

  updateForm(comment: IComment) {
    this.editForm.patchValue({
      id: comment.id,
      commentTitle: comment.commentTitle,
      commentBody: comment.commentBody,
      commentMedia: comment.commentMedia,
      commentMediaContentType: comment.commentMediaContentType,
      dateAdded: comment.dateAdded,
      dateApproved: comment.dateApproved,
      reply: comment.reply,
      user: comment.user,
      watchlist: comment.watchlist
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
    const comment = this.createFromForm();
    if (comment.id !== undefined) {
      this.subscribeToSaveResponse(this.commentService.update(comment));
    } else {
      this.subscribeToSaveResponse(this.commentService.create(comment));
    }
  }

  private createFromForm(): IComment {
    const entity = {
      ...new Comment(),
      id: this.editForm.get(['id']).value,
      commentTitle: this.editForm.get(['commentTitle']).value,
      commentBody: this.editForm.get(['commentBody']).value,
      commentMediaContentType: this.editForm.get(['commentMediaContentType']).value,
      commentMedia: this.editForm.get(['commentMedia']).value,
      dateAdded: this.editForm.get(['dateAdded']).value,
      dateApproved: this.editForm.get(['dateApproved']).value,
      reply: this.editForm.get(['reply']).value,
      user: this.editForm.get(['user']).value,
      watchlist: this.editForm.get(['watchlist']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>) {
    result.subscribe((res: HttpResponse<IComment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCommentById(index: number, item: IComment) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackPostById(index: number, item: IPost) {
    return item.id;
  }

  trackWatchlistById(index: number, item: IWatchlist) {
    return item.id;
  }
}
