import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

export interface ILienDocMetaGroupe {
  id?: number;
  nom?: string;
  typeDuDoc?: TypeDoc;
  iconeContentType?: string;
  icone?: any;
  imageContentType?: string;
  image?: any;
  docContentType?: string;
  doc?: any;
  detail?: any;
  groupeNom?: string;
  groupeId?: number;
  typeTypeDuGroupe?: string;
  typeId?: number;
}

export class LienDocMetaGroupe implements ILienDocMetaGroupe {
  constructor(
    public id?: number,
    public nom?: string,
    public typeDuDoc?: TypeDoc,
    public iconeContentType?: string,
    public icone?: any,
    public imageContentType?: string,
    public image?: any,
    public docContentType?: string,
    public doc?: any,
    public detail?: any,
    public groupeNom?: string,
    public groupeId?: number,
    public typeTypeDuGroupe?: string,
    public typeId?: number
  ) {}
}
