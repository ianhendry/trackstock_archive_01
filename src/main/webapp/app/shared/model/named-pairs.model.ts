import { IUser } from 'app/core/user/user.model';

export interface INamedPairs {
  id?: number;
  groupName?: string;
  key?: string;
  value?: string;
  user?: IUser;
}

export class NamedPairs implements INamedPairs {
  constructor(public id?: number, public groupName?: string, public key?: string, public value?: string, public user?: IUser) {}
}
