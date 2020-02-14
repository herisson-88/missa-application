import { Moment } from 'moment';
import { Day } from 'app/shared/model/enumerations/day.model';

export interface IDateMetaGroupe {
  id?: number;
  dateDebut?: Moment;
  dateFin?: Moment;
  every?: Day;
  hour?: number;
  minutes?: number;
  detail?: any;
  groupeNom?: string;
  groupeId?: number;
}

export class DateMetaGroupe implements IDateMetaGroupe {
  constructor(
    public id?: number,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public every?: Day,
    public hour?: number,
    public minutes?: number,
    public detail?: any,
    public groupeNom?: string,
    public groupeId?: number
  ) {}
}
