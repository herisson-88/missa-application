import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMessageMetaGroupe } from 'app/shared/model/message-meta-groupe.model';

type EntityResponseType = HttpResponse<IMessageMetaGroupe>;
type EntityArrayResponseType = HttpResponse<IMessageMetaGroupe[]>;

@Injectable({ providedIn: 'root' })
export class MessageMetaGroupeService {
  public resourceUrl = SERVER_API_URL + 'api/message-meta-groupes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/message-meta-groupes';

  constructor(protected http: HttpClient) {}

  create(messageMetaGroupe: IMessageMetaGroupe): Observable<EntityResponseType> {
    return this.http.post<IMessageMetaGroupe>(this.resourceUrl, messageMetaGroupe, { observe: 'response' });
  }

  update(messageMetaGroupe: IMessageMetaGroupe): Observable<EntityResponseType> {
    return this.http.put<IMessageMetaGroupe>(this.resourceUrl, messageMetaGroupe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMessageMetaGroupe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMessageMetaGroupe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMessageMetaGroupe[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
