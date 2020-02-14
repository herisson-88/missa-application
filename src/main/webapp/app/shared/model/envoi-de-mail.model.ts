import { Moment } from 'moment';
import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';

export interface IEnvoiDeMail {
  id?: number;
  dateEnvoi?: Moment;
  titre?: string;
  adresseMail?: any;
  motSpirituel?: any;
  conseilTechnique?: any;
  nbDestinataire?: number;
  sended?: boolean;
  groupePartageParMails?: IMetaGroupe[];
  adminId?: number;
  groupeDuMailNom?: string;
  groupeDuMailId?: number;
}

export class EnvoiDeMail implements IEnvoiDeMail {
  constructor(
    public id?: number,
    public dateEnvoi?: Moment,
    public titre?: string,
    public adresseMail?: any,
    public motSpirituel?: any,
    public conseilTechnique?: any,
    public nbDestinataire?: number,
    public sended?: boolean,
    public groupePartageParMails?: IMetaGroupe[],
    public adminId?: number,
    public groupeDuMailNom?: string,
    public groupeDuMailId?: number
  ) {
    this.sended = this.sended || false;
  }
}
