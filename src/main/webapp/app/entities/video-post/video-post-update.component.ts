import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IVideoPost, VideoPost } from 'app/shared/model/video-post.model';
import { VideoPostService } from './video-post.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-video-post-update',
  templateUrl: './video-post-update.component.html'
})
export class VideoPostUpdateComponent implements OnInit {
  videoPost: IVideoPost;
  isSaving: boolean;

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
    user: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected videoPostService: VideoPostService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ videoPost }) => {
      this.updateForm(videoPost);
      this.videoPost = videoPost;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(videoPost: IVideoPost) {
    this.editForm.patchValue({
      id: videoPost.id,
      postTitle: videoPost.postTitle,
      postBody: videoPost.postBody,
      dateAdded: videoPost.dateAdded,
      dateApproved: videoPost.dateApproved,
      media1: videoPost.media1,
      media1ContentType: videoPost.media1ContentType,
      user: videoPost.user
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

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const videoPost = this.createFromForm();
    if (videoPost.id !== undefined) {
      this.subscribeToSaveResponse(this.videoPostService.update(videoPost));
    } else {
      this.subscribeToSaveResponse(this.videoPostService.create(videoPost));
    }
  }

  private createFromForm(): IVideoPost {
    const entity = {
      ...new VideoPost(),
      id: this.editForm.get(['id']).value,
      postTitle: this.editForm.get(['postTitle']).value,
      postBody: this.editForm.get(['postBody']).value,
      dateAdded: this.editForm.get(['dateAdded']).value,
      dateApproved: this.editForm.get(['dateApproved']).value,
      media1ContentType: this.editForm.get(['media1ContentType']).value,
      media1: this.editForm.get(['media1']).value,
      user: this.editForm.get(['user']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideoPost>>) {
    result.subscribe((res: HttpResponse<IVideoPost>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
