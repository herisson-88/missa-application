export interface IMessageMetaGroupe {
  id?: number;
  titre?: string;
  publique?: boolean;
  message?: any;
  auteurId?: number;
  groupeNom?: string;
  groupeId?: number;
}

export class MessageMetaGroupe implements IMessageMetaGroupe {
  constructor(
    public id?: number,
    public titre?: string,
    public publique?: boolean,
    public message?: any,
    public auteurId?: number,
    public groupeNom?: string,
    public groupeId?: number
  ) {
    this.publique = this.publique || false;
  }
}
