import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IVideoPost {
  id?: number;
  postTitle?: string;
  postBody?: string;
  dateAdded?: Moment;
  dateApproved?: Moment;
  media1ContentType?: string;
  media1?: any;
  user?: IUser;
}

export class VideoPost implements IVideoPost {
  constructor(
    public id?: number,
    public postTitle?: string,
    public postBody?: string,
    public dateAdded?: Moment,
    public dateApproved?: Moment,
    public media1ContentType?: string,
    public media1?: any,
    public user?: IUser
  ) {}
}
