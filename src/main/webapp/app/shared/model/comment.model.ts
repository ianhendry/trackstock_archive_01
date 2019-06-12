import { Moment } from 'moment';
import { IComment } from 'app/shared/model/comment.model';
import { IUser } from 'app/core/user/user.model';
import { IPost } from 'app/shared/model/post.model';
import { IWatchlist } from 'app/shared/model/watchlist.model';

export interface IComment {
  id?: number;
  commentTitle?: string;
  commentBody?: string;
  commentMediaContentType?: string;
  commentMedia?: any;
  dateAdded?: Moment;
  dateApproved?: Moment;
  reply?: IComment;
  user?: IUser;
  post?: IPost;
  watchlist?: IWatchlist;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public commentTitle?: string,
    public commentBody?: string,
    public commentMediaContentType?: string,
    public commentMedia?: any,
    public dateAdded?: Moment,
    public dateApproved?: Moment,
    public reply?: IComment,
    public user?: IUser,
    public post?: IPost,
    public watchlist?: IWatchlist
  ) {}
}
