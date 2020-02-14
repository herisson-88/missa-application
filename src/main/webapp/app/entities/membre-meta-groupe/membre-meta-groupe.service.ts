import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMembreMetaGroupe } from 'app/shared/model/membre-meta-groupe.model';

type EntityResponseType = HttpResponse<IMembreMetaGroupe>;
type EntityArrayResponseType = HttpResponse<IMembreMetaGroupe[]>;

@Injectable({ providedIn: 'root' })
export class MembreMetaGroupeService {
  public resourceUrl = SERVER_API_URL + 'api/membre-meta-groupes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/membre-meta-groupes';

  constructor(protected http: HttpClient) {}

  create(membreMetaGroupe: IMembreMetaGroupe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membreMetaGroupe);
    return this.http
      .post<IMembreMetaGroupe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(membreMetaGroupe: IMembreMetaGroupe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membreMetaGroupe);
    return this.http
      .put<IMembreMetaGroupe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMembreMetaGroupe>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMembreMetaGroupe[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMembreMetaGroupe[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(membreMetaGroupe: IMembreMetaGroupe): IMembreMetaGroupe {
    const copy: IMembreMetaGroupe = Object.assign({}, membreMetaGroupe, {
      dateValidation:
        membreMetaGroupe.dateValidation && membreMetaGroupe.dateValidation.isValid() ? membreMetaGroupe.dateValidation.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateValidation = res.body.dateValidation ? moment(res.body.dateValidation) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((membreMetaGroupe: IMembreMetaGroupe) => {
        membreMetaGroupe.dateValidation = membreMetaGroupe.dateValidation ? moment(membreMetaGroupe.dateValidation) : undefined;
      });
    }
    return res;
  }
}
