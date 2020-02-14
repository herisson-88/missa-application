import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ILienDocMetaGroupe } from 'app/shared/model/lien-doc-meta-groupe.model';

type EntityResponseType = HttpResponse<ILienDocMetaGroupe>;
type EntityArrayResponseType = HttpResponse<ILienDocMetaGroupe[]>;

@Injectable({ providedIn: 'root' })
export class LienDocMetaGroupeService {
  public resourceUrl = SERVER_API_URL + 'api/lien-doc-meta-groupes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/lien-doc-meta-groupes';

  constructor(protected http: HttpClient) {}

  create(lienDocMetaGroupe: ILienDocMetaGroupe): Observable<EntityResponseType> {
    return this.http.post<ILienDocMetaGroupe>(this.resourceUrl, lienDocMetaGroupe, { observe: 'response' });
  }

  update(lienDocMetaGroupe: ILienDocMetaGroupe): Observable<EntityResponseType> {
    return this.http.put<ILienDocMetaGroupe>(this.resourceUrl, lienDocMetaGroupe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILienDocMetaGroupe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILienDocMetaGroupe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILienDocMetaGroupe[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
