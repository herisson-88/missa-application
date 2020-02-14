import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IDateMetaGroupe } from 'app/shared/model/date-meta-groupe.model';

type EntityResponseType = HttpResponse<IDateMetaGroupe>;
type EntityArrayResponseType = HttpResponse<IDateMetaGroupe[]>;

@Injectable({ providedIn: 'root' })
export class DateMetaGroupeService {
  public resourceUrl = SERVER_API_URL + 'api/date-meta-groupes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/date-meta-groupes';

  constructor(protected http: HttpClient) {}

  create(dateMetaGroupe: IDateMetaGroupe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dateMetaGroupe);
    return this.http
      .post<IDateMetaGroupe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dateMetaGroupe: IDateMetaGroupe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dateMetaGroupe);
    return this.http
      .put<IDateMetaGroupe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDateMetaGroupe>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDateMetaGroupe[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDateMetaGroupe[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(dateMetaGroupe: IDateMetaGroupe): IDateMetaGroupe {
    const copy: IDateMetaGroupe = Object.assign({}, dateMetaGroupe, {
      dateDebut: dateMetaGroupe.dateDebut && dateMetaGroupe.dateDebut.isValid() ? dateMetaGroupe.dateDebut.toJSON() : undefined,
      dateFin: dateMetaGroupe.dateFin && dateMetaGroupe.dateFin.isValid() ? dateMetaGroupe.dateFin.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? moment(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? moment(res.body.dateFin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dateMetaGroupe: IDateMetaGroupe) => {
        dateMetaGroupe.dateDebut = dateMetaGroupe.dateDebut ? moment(dateMetaGroupe.dateDebut) : undefined;
        dateMetaGroupe.dateFin = dateMetaGroupe.dateFin ? moment(dateMetaGroupe.dateFin) : undefined;
      });
    }
    return res;
  }
}
