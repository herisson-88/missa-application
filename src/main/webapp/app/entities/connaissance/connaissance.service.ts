import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IConnaissance } from 'app/shared/model/connaissance.model';

type EntityResponseType = HttpResponse<IConnaissance>;
type EntityArrayResponseType = HttpResponse<IConnaissance[]>;

@Injectable({ providedIn: 'root' })
export class ConnaissanceService {
  public resourceUrl = SERVER_API_URL + 'api/connaissances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/connaissances';

  constructor(protected http: HttpClient) {}

  create(connaissance: IConnaissance): Observable<EntityResponseType> {
    return this.http.post<IConnaissance>(this.resourceUrl, connaissance, { observe: 'response' });
  }

  update(connaissance: IConnaissance): Observable<EntityResponseType> {
    return this.http.put<IConnaissance>(this.resourceUrl, connaissance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConnaissance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConnaissance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConnaissance[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
