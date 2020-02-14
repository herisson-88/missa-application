import { IConnaissance } from 'app/shared/model/connaissance.model';
import { IMembreMetaGroupe } from 'app/shared/model/membre-meta-groupe.model';
import { IMessageMetaGroupe } from 'app/shared/model/message-meta-groupe.model';
import { IEnvoiDeMail } from 'app/shared/model/envoi-de-mail.model';
import { IPartageMetaGroupe } from 'app/shared/model/partage-meta-groupe.model';
import { EtatUser } from 'app/shared/model/enumerations/etat-user.model';

export interface IMissaUser {
  id?: number;
  codePostal?: string;
  initiales?: string;
  nom?: string;
  prenom?: string;
  mail?: string;
  etat?: EtatUser;
  userId?: number;
  connais?: IConnaissance[];
  membreValides?: IMembreMetaGroupe[];
  membres?: IMembreMetaGroupe[];
  messagesDeMois?: IMessageMetaGroupe[];
  mails?: IEnvoiDeMail[];
  demandesPartages?: IPartageMetaGroupe[];
  aValidePartages?: IPartageMetaGroupe[];
}

export class MissaUser implements IMissaUser {
  constructor(
    public id?: number,
    public codePostal?: string,
    public initiales?: string,
    public nom?: string,
    public prenom?: string,
    public mail?: string,
    public etat?: EtatUser,
    public userId?: number,
    public connais?: IConnaissance[],
    public membreValides?: IMembreMetaGroupe[],
    public membres?: IMembreMetaGroupe[],
    public messagesDeMois?: IMessageMetaGroupe[],
    public mails?: IEnvoiDeMail[],
    public demandesPartages?: IPartageMetaGroupe[],
    public aValidePartages?: IPartageMetaGroupe[]
  ) {}
}
