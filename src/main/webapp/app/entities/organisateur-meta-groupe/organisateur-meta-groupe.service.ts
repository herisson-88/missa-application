import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IOrganisateurMetaGroupe } from 'app/shared/model/organisateur-meta-groupe.model';

type EntityResponseType = HttpResponse<IOrganisateurMetaGroupe>;
type EntityArrayResponseType = HttpResponse<IOrganisateurMetaGroupe[]>;

@Injectable({ providedIn: 'root' })
export class OrganisateurMetaGroupeService {
  public resourceUrl = SERVER_API_URL + 'api/organisateur-meta-groupes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/organisateur-meta-groupes';

  constructor(protected http: HttpClient) {}

  create(organisateurMetaGroupe: IOrganisateurMetaGroupe): Observable<EntityResponseType> {
    return this.http.post<IOrganisateurMetaGroupe>(this.resourceUrl, organisateurMetaGroupe, { observe: 'response' });
  }

  update(organisateurMetaGroupe: IOrganisateurMetaGroupe): Observable<EntityResponseType> {
    return this.http.put<IOrganisateurMetaGroupe>(this.resourceUrl, organisateurMetaGroupe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrganisateurMetaGroupe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganisateurMetaGroupe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganisateurMetaGroupe[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
