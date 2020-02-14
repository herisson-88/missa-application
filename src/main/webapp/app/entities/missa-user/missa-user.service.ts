import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMissaUser } from 'app/shared/model/missa-user.model';

type EntityResponseType = HttpResponse<IMissaUser>;
type EntityArrayResponseType = HttpResponse<IMissaUser[]>;

@Injectable({ providedIn: 'root' })
export class MissaUserService {
  public resourceUrl = SERVER_API_URL + 'api/missa-users';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/missa-users';

  constructor(protected http: HttpClient) {}

  create(missaUser: IMissaUser): Observable<EntityResponseType> {
    return this.http.post<IMissaUser>(this.resourceUrl, missaUser, { observe: 'response' });
  }

  update(missaUser: IMissaUser): Observable<EntityResponseType> {
    return this.http.put<IMissaUser>(this.resourceUrl, missaUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMissaUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMissaUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMissaUser[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
