export interface IOrganisateurMetaGroupe {
  id?: number;
  site?: string;
  nom?: string;
  prenom?: string;
  telephone?: string;
  mail?: string;
  adresse?: string;
  codePostal?: string;
  ville?: string;
  detail?: any;
  groupeNom?: string;
  groupeId?: number;
}

export class OrganisateurMetaGroupe implements IOrganisateurMetaGroupe {
  constructor(
    public id?: number,
    public site?: string,
    public nom?: string,
    public prenom?: string,
    public telephone?: string,
    public mail?: string,
    public adresse?: string,
    public codePostal?: string,
    public ville?: string,
    public detail?: any,
    public groupeNom?: string,
    public groupeId?: number
  ) {}
}
