import { Moment } from 'moment';

export interface IPartageMetaGroupe {
  id?: number;
  validated?: boolean;
  dateValidation?: Moment;
  detail?: any;
  metaGroupePartageNom?: string;
  metaGroupePartageId?: number;
  metaGroupeDestinatairesNom?: string;
  metaGroupeDestinatairesId?: number;
  auteurPartageId?: number;
  validateurDuPartageId?: number;
}

export class PartageMetaGroupe implements IPartageMetaGroupe {
  constructor(
    public id?: number,
    public validated?: boolean,
    public dateValidation?: Moment,
    public detail?: any,
    public metaGroupePartageNom?: string,
    public metaGroupePartageId?: number,
    public metaGroupeDestinatairesNom?: string,
    public metaGroupeDestinatairesId?: number,
    public auteurPartageId?: number,
    public validateurDuPartageId?: number
  ) {
    this.validated = this.validated || false;
  }
}
