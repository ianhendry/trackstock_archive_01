import { Moment } from 'moment';
import { IInstrument } from 'app/shared/model/instrument.model';
import { ITradingAccount } from 'app/shared/model/trading-account.model';

export interface IPosition {
  id?: number;
  positionTradePlan?: string;
  positionOpenDate?: Moment;
  positionOpenPrice?: number;
  positionCloseDate?: Moment;
  positionClosePrice?: number;
  positioWinLoss?: boolean;
  positionProfitAmount?: number;
  positionClosingThought?: string;
  instrument?: IInstrument;
  tradingAccount?: ITradingAccount;
}

export class Position implements IPosition {
  constructor(
    public id?: number,
    public positionTradePlan?: string,
    public positionOpenDate?: Moment,
    public positionOpenPrice?: number,
    public positionCloseDate?: Moment,
    public positionClosePrice?: number,
    public positioWinLoss?: boolean,
    public positionProfitAmount?: number,
    public positionClosingThought?: string,
    public instrument?: IInstrument,
    public tradingAccount?: ITradingAccount
  ) {
    this.positioWinLoss = this.positioWinLoss || false;
  }
}
