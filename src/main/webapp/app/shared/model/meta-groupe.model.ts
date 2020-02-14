import { Moment } from 'moment';
import { IMembreMetaGroupe } from 'app/shared/model/membre-meta-groupe.model';
import { IDateMetaGroupe } from 'app/shared/model/date-meta-groupe.model';
import { ILieuMetaGroupe } from 'app/shared/model/lieu-meta-groupe.model';
import { ILienDocMetaGroupe } from 'app/shared/model/lien-doc-meta-groupe.model';
import { IOrganisateurMetaGroupe } from 'app/shared/model/organisateur-meta-groupe.model';
import { IMessageMetaGroupe } from 'app/shared/model/message-meta-groupe.model';
import { IEnvoiDeMail } from 'app/shared/model/envoi-de-mail.model';
import { IPartageMetaGroupe } from 'app/shared/model/partage-meta-groupe.model';
import { MembreDiffusion } from 'app/shared/model/enumerations/membre-diffusion.model';
import { Visibilite } from 'app/shared/model/enumerations/visibilite.model';

export interface IMetaGroupe {
  id?: number;
  nom?: string;
  autoriteValidation?: boolean;
  membreParent?: MembreDiffusion;
  visibilite?: Visibilite;
  droitEnvoiDeMail?: boolean;
  demandeDiffusionVerticale?: boolean;
  messagerieModeree?: boolean;
  groupeFinal?: boolean;
  dateValidation?: Moment;
  detail?: any;
  membres?: IMembreMetaGroupe[];
  dates?: IDateMetaGroupe[];
  lieus?: ILieuMetaGroupe[];
  docs?: ILienDocMetaGroupe[];
  coordonneesOrganisateurs?: IOrganisateurMetaGroupe[];
  sousGroupes?: IMetaGroupe[];
  messagesDuGroupes?: IMessageMetaGroupe[];
  mails?: IEnvoiDeMail[];
  partagesVers?: IPartageMetaGroupe[];
  partagesRecuses?: IPartageMetaGroupe[];
  parentNom?: string;
  parentId?: number;
  typeId?: number;
  reseauId?: number;
  messageMailReferents?: IEnvoiDeMail[];
}

export class MetaGroupe implements IMetaGroupe {
  constructor(
    public id?: number,
    public nom?: string,
    public autoriteValidation?: boolean,
    public membreParent?: MembreDiffusion,
    public visibilite?: Visibilite,
    public droitEnvoiDeMail?: boolean,
    public demandeDiffusionVerticale?: boolean,
    public messagerieModeree?: boolean,
    public groupeFinal?: boolean,
    public dateValidation?: Moment,
    public detail?: any,
    public membres?: IMembreMetaGroupe[],
    public dates?: IDateMetaGroupe[],
    public lieus?: ILieuMetaGroupe[],
    public docs?: ILienDocMetaGroupe[],
    public coordonneesOrganisateurs?: IOrganisateurMetaGroupe[],
    public sousGroupes?: IMetaGroupe[],
    public messagesDuGroupes?: IMessageMetaGroupe[],
    public mails?: IEnvoiDeMail[],
    public partagesVers?: IPartageMetaGroupe[],
    public partagesRecuses?: IPartageMetaGroupe[],
    public parentNom?: string,
    public parentId?: number,
    public typeId?: number,
    public reseauId?: number,
    public messageMailReferents?: IEnvoiDeMail[]
  ) {
    this.autoriteValidation = this.autoriteValidation || false;
    this.droitEnvoiDeMail = this.droitEnvoiDeMail || false;
    this.demandeDiffusionVerticale = this.demandeDiffusionVerticale || false;
    this.messagerieModeree = this.messagerieModeree || false;
    this.groupeFinal = this.groupeFinal || false;
  }
}
