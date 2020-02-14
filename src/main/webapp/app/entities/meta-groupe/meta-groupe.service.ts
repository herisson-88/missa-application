import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';

type EntityResponseType = HttpResponse<IMetaGroupe>;
type EntityArrayResponseType = HttpResponse<IMetaGroupe[]>;

@Injectable({ providedIn: 'root' })
export class MetaGroupeService {
  public resourceUrl = SERVER_API_URL + 'api/meta-groupes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/meta-groupes';

  constructor(protected http: HttpClient) {}

  create(metaGroupe: IMetaGroupe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(metaGroupe);
    return this.http
      .post<IMetaGroupe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(metaGroupe: IMetaGroupe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(metaGroupe);
    return this.http
      .put<IMetaGroupe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMetaGroupe>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMetaGroupe[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMetaGroupe[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(metaGroupe: IMetaGroupe): IMetaGroupe {
    const copy: IMetaGroupe = Object.assign({}, metaGroupe, {
      dateValidation: metaGroupe.dateValidation && metaGroupe.dateValidation.isValid() ? metaGroupe.dateValidation.toJSON() : undefined
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
      res.body.forEach((metaGroupe: IMetaGroupe) => {
        metaGroupe.dateValidation = metaGroupe.dateValidation ? moment(metaGroupe.dateValidation) : undefined;
      });
    }
    return res;
  }
}
