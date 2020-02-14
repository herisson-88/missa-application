import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';

type EntityResponseType = HttpResponse<ITypeMetaGroupe>;
type EntityArrayResponseType = HttpResponse<ITypeMetaGroupe[]>;

@Injectable({ providedIn: 'root' })
export class TypeMetaGroupeService {
  public resourceUrl = SERVER_API_URL + 'api/type-meta-groupes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/type-meta-groupes';

  constructor(protected http: HttpClient) {}

  create(typeMetaGroupe: ITypeMetaGroupe): Observable<EntityResponseType> {
    return this.http.post<ITypeMetaGroupe>(this.resourceUrl, typeMetaGroupe, { observe: 'response' });
  }

  update(typeMetaGroupe: ITypeMetaGroupe): Observable<EntityResponseType> {
    return this.http.put<ITypeMetaGroupe>(this.resourceUrl, typeMetaGroupe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeMetaGroupe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeMetaGroupe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeMetaGroupe[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
