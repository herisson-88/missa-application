import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IReseau } from 'app/shared/model/reseau.model';

type EntityResponseType = HttpResponse<IReseau>;
type EntityArrayResponseType = HttpResponse<IReseau[]>;

@Injectable({ providedIn: 'root' })
export class ReseauService {
  public resourceUrl = SERVER_API_URL + 'api/reseaus';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/reseaus';

  constructor(protected http: HttpClient) {}

  create(reseau: IReseau): Observable<EntityResponseType> {
    return this.http.post<IReseau>(this.resourceUrl, reseau, { observe: 'response' });
  }

  update(reseau: IReseau): Observable<EntityResponseType> {
    return this.http.put<IReseau>(this.resourceUrl, reseau, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReseau>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReseau[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReseau[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
