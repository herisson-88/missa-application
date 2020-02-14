export interface IConnaissance {
  id?: number;
  nom?: string;
  prenom?: string;
  mail?: string;
  idFaceBook?: string;
  parraine?: boolean;
  connuParId?: number;
}

export class Connaissance implements IConnaissance {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public mail?: string,
    public idFaceBook?: string,
    public parraine?: boolean,
    public connuParId?: number
  ) {
    this.parraine = this.parraine || false;
  }
}
