import { Moment } from 'moment';
import { IPosition } from 'app/shared/model/position.model';
import { IUser } from 'app/core/user/user.model';

export interface ITradingAccount {
  id?: number;
  accountName?: string;
  accountReal?: boolean;
  accountOpenDate?: Moment;
  accountBalance?: number;
  accountCloseDate?: Moment;
  positions?: IPosition[];
  user?: IUser;
}

export class TradingAccount implements ITradingAccount {
  constructor(
    public id?: number,
    public accountName?: string,
    public accountReal?: boolean,
    public accountOpenDate?: Moment,
    public accountBalance?: number,
    public accountCloseDate?: Moment,
    public positions?: IPosition[],
    public user?: IUser
  ) {
    this.accountReal = this.accountReal || false;
  }
}
