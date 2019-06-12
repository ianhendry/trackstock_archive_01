import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IShippingDetails {
  id?: number;
  userName?: string;
  email?: string;
  name?: string;
  address1?: string;
  address2?: string;
  address3?: string;
  address4?: string;
  address5?: string;
  dateAdded?: Moment;
  dateInactive?: Moment;
  userPictureContentType?: string;
  userPicture?: any;
  user?: IUser;
}

export class ShippingDetails implements IShippingDetails {
  constructor(
    public id?: number,
    public userName?: string,
    public email?: string,
    public name?: string,
    public address1?: string,
    public address2?: string,
    public address3?: string,
    public address4?: string,
    public address5?: string,
    public dateAdded?: Moment,
    public dateInactive?: Moment,
    public userPictureContentType?: string,
    public userPicture?: any,
    public user?: IUser
  ) {}
}
