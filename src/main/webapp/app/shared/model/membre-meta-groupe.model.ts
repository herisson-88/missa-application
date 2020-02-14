import { Moment } from 'moment';

export interface IMembreMetaGroupe {
  id?: number;
  validated?: boolean;
  dateValidation?: Moment;
  admin?: boolean;
  presentation?: any;
  connaissance?: any;
  mailingList?: boolean;
  groupeMembreNom?: string;
  groupeMembreId?: number;
  valideParId?: number;
  missaUserId?: number;
}

export class MembreMetaGroupe implements IMembreMetaGroupe {
  constructor(
    public id?: number,
    public validated?: boolean,
    public dateValidation?: Moment,
    public admin?: boolean,
    public presentation?: any,
    public connaissance?: any,
    public mailingList?: boolean,
    public groupeMembreNom?: string,
    public groupeMembreId?: number,
    public valideParId?: number,
    public missaUserId?: number
  ) {
    this.validated = this.validated || false;
    this.admin = this.admin || false;
    this.mailingList = this.mailingList || false;
  }
}
