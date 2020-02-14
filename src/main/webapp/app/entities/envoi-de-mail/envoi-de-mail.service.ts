import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IEnvoiDeMail } from 'app/shared/model/envoi-de-mail.model';

type EntityResponseType = HttpResponse<IEnvoiDeMail>;
type EntityArrayResponseType = HttpResponse<IEnvoiDeMail[]>;

@Injectable({ providedIn: 'root' })
export class EnvoiDeMailService {
  public resourceUrl = SERVER_API_URL + 'api/envoi-de-mails';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/envoi-de-mails';

  constructor(protected http: HttpClient) {}

  create(envoiDeMail: IEnvoiDeMail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(envoiDeMail);
    return this.http
      .post<IEnvoiDeMail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(envoiDeMail: IEnvoiDeMail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(envoiDeMail);
    return this.http
      .put<IEnvoiDeMail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEnvoiDeMail>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEnvoiDeMail[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEnvoiDeMail[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(envoiDeMail: IEnvoiDeMail): IEnvoiDeMail {
    const copy: IEnvoiDeMail = Object.assign({}, envoiDeMail, {
      dateEnvoi: envoiDeMail.dateEnvoi && envoiDeMail.dateEnvoi.isValid() ? envoiDeMail.dateEnvoi.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateEnvoi = res.body.dateEnvoi ? moment(res.body.dateEnvoi) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((envoiDeMail: IEnvoiDeMail) => {
        envoiDeMail.dateEnvoi = envoiDeMail.dateEnvoi ? moment(envoiDeMail.dateEnvoi) : undefined;
      });
    }
    return res;
  }
}
