import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';
import { ITypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';

export interface IReseau {
  id?: number;
  nom?: string;
  groupes?: IMetaGroupe[];
  types?: ITypeMetaGroupe[];
}

export class Reseau implements IReseau {
  constructor(public id?: number, public nom?: string, public groupes?: IMetaGroupe[], public types?: ITypeMetaGroupe[]) {}
}
