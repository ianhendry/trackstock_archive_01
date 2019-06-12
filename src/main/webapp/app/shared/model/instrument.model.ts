import { Moment } from 'moment';
import { IPosition } from 'app/shared/model/position.model';
import { IPost } from 'app/shared/model/post.model';
import { IWatchlist } from 'app/shared/model/watchlist.model';

export const enum FinancialDataSources {
  FXPRO = 'FXPRO',
  QUANDLL = 'QUANDLL',
  TRADENAVIGATOR = 'TRADENAVIGATOR'
}

export interface IInstrument {
  id?: number;
  dataProvider?: FinancialDataSources;
  instrumentTicker?: string;
  instrumentExchnage?: string;
  instrumentDescription?: string;
  instrumentDataFrom?: string;
  instrumentActive?: boolean;
  dateAdded?: Moment;
  dateInactive?: Moment;
  position?: IPosition;
  post?: IPost;
  watchlists?: IWatchlist[];
}

export class Instrument implements IInstrument {
  constructor(
    public id?: number,
    public dataProvider?: FinancialDataSources,
    public instrumentTicker?: string,
    public instrumentExchnage?: string,
    public instrumentDescription?: string,
    public instrumentDataFrom?: string,
    public instrumentActive?: boolean,
    public dateAdded?: Moment,
    public dateInactive?: Moment,
    public position?: IPosition,
    public post?: IPost,
    public watchlists?: IWatchlist[]
  ) {
    this.instrumentActive = this.instrumentActive || false;
  }
}
