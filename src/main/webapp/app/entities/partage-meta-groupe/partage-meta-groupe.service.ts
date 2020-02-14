import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPartageMetaGroupe } from 'app/shared/model/partage-meta-groupe.model';

type EntityResponseType = HttpResponse<IPartageMetaGroupe>;
type EntityArrayResponseType = HttpResponse<IPartageMetaGroupe[]>;

@Injectable({ providedIn: 'root' })
export class PartageMetaGroupeService {
  public resourceUrl = SERVER_API_URL + 'api/partage-meta-groupes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/partage-meta-groupes';

  constructor(protected http: HttpClient) {}

  create(partageMetaGroupe: IPartageMetaGroupe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partageMetaGroupe);
    return this.http
      .post<IPartageMetaGroupe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partageMetaGroupe: IPartageMetaGroupe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partageMetaGroupe);
    return this.http
      .put<IPartageMetaGroupe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartageMetaGroupe>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartageMetaGroupe[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartageMetaGroupe[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(partageMetaGroupe: IPartageMetaGroupe): IPartageMetaGroupe {
    const copy: IPartageMetaGroupe = Object.assign({}, partageMetaGroupe, {
      dateValidation:
        partageMetaGroupe.dateValidation && partageMetaGroupe.dateValidation.isValid()
          ? partageMetaGroupe.dateValidation.toJSON()
          : undefined
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
      res.body.forEach((partageMetaGroupe: IPartageMetaGroupe) => {
        partageMetaGroupe.dateValidation = partageMetaGroupe.dateValidation ? moment(partageMetaGroupe.dateValidation) : undefined;
      });
    }
    return res;
  }
}
