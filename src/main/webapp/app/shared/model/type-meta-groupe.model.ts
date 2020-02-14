import { ILienDocMetaGroupe } from 'app/shared/model/lien-doc-meta-groupe.model';
import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';

export interface ITypeMetaGroupe {
  id?: number;
  typeDuGroupe?: string;
  iconeFa?: string;
  detail?: any;
  ordreMail?: number;
  defaultDocs?: ILienDocMetaGroupe[];
  groupes?: IMetaGroupe[];
  reseauId?: number;
}

export class TypeMetaGroupe implements ITypeMetaGroupe {
  constructor(
    public id?: number,
    public typeDuGroupe?: string,
    public iconeFa?: string,
    public detail?: any,
    public ordreMail?: number,
    public defaultDocs?: ILienDocMetaGroupe[],
    public groupes?: IMetaGroupe[],
    public reseauId?: number
  ) {}
}
