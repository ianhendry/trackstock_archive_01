import { Moment } from 'moment';
import { IComment } from 'app/shared/model/comment.model';
import { IInstrument } from 'app/shared/model/instrument.model';

export interface IWatchlist {
  id?: number;
  watchlistName?: string;
  watchlistDescription?: string;
  dateCreated?: Moment;
  dateInactive?: Moment;
  watchlistInactive?: boolean;
  comments?: IComment[];
  instruments?: IInstrument[];
}

export class Watchlist implements IWatchlist {
  constructor(
    public id?: number,
    public watchlistName?: string,
    public watchlistDescription?: string,
    public dateCreated?: Moment,
    public dateInactive?: Moment,
    public watchlistInactive?: boolean,
    public comments?: IComment[],
    public instruments?: IInstrument[]
  ) {
    this.watchlistInactive = this.watchlistInactive || false;
  }
}
