export interface ILieuMetaGroupe {
  id?: number;
  nom?: string;
  adresse?: string;
  codePostal?: string;
  ville?: string;
  lat?: number;
  lon?: number;
  detail?: any;
  departementGroupe?: string;
  groupeNom?: string;
  groupeId?: number;
}

export class LieuMetaGroupe implements ILieuMetaGroupe {
  constructor(
    public id?: number,
    public nom?: string,
    public adresse?: string,
    public codePostal?: string,
    public ville?: string,
    public lat?: number,
    public lon?: number,
    public detail?: any,
    public departementGroupe?: string,
    public groupeNom?: string,
    public groupeId?: number
  ) {}
}
