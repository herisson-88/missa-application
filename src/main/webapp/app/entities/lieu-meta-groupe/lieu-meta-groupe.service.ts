import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ILieuMetaGroupe } from 'app/shared/model/lieu-meta-groupe.model';

type EntityResponseType = HttpResponse<ILieuMetaGroupe>;
type EntityArrayResponseType = HttpResponse<ILieuMetaGroupe[]>;

@Injectable({ providedIn: 'root' })
export class LieuMetaGroupeService {
  public resourceUrl = SERVER_API_URL + 'api/lieu-meta-groupes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/lieu-meta-groupes';

  constructor(protected http: HttpClient) {}

  create(lieuMetaGroupe: ILieuMetaGroupe): Observable<EntityResponseType> {
    return this.http.post<ILieuMetaGroupe>(this.resourceUrl, lieuMetaGroupe, { observe: 'response' });
  }

  update(lieuMetaGroupe: ILieuMetaGroupe): Observable<EntityResponseType> {
    return this.http.put<ILieuMetaGroupe>(this.resourceUrl, lieuMetaGroupe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILieuMetaGroupe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILieuMetaGroupe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILieuMetaGroupe[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
