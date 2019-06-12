import { Moment } from 'moment';
import { IInstrument } from 'app/shared/model/instrument.model';
import { IComment } from 'app/shared/model/comment.model';
import { IUser } from 'app/core/user/user.model';

export interface IPost {
  id?: number;
  postTitle?: string;
  postBody?: string;
  dateAdded?: Moment;
  dateApproved?: Moment;
  media1ContentType?: string;
  media1?: any;
  media2ContentType?: string;
  media2?: any;
  media3ContentType?: string;
  media3?: any;
  media4ContentType?: string;
  media4?: any;
  instrument?: IInstrument;
  comment?: IComment;
  user?: IUser;
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public postTitle?: string,
    public postBody?: string,
    public dateAdded?: Moment,
    public dateApproved?: Moment,
    public media1ContentType?: string,
    public media1?: any,
    public media2ContentType?: string,
    public media2?: any,
    public media3ContentType?: string,
    public media3?: any,
    public media4ContentType?: string,
    public media4?: any,
    public instrument?: IInstrument,
    public comment?: IComment,
    public user?: IUser
  ) {}
}
