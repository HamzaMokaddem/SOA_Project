import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILune } from 'app/shared/model/lune.model';

type EntityResponseType = HttpResponse<ILune>;
type EntityArrayResponseType = HttpResponse<ILune[]>;

@Injectable({ providedIn: 'root' })
export class LuneService {
  public resourceUrl = SERVER_API_URL + 'api/lunes';

  constructor(protected http: HttpClient) {}

  create(lune: ILune): Observable<EntityResponseType> {
    return this.http.post<ILune>(this.resourceUrl, lune, { observe: 'response' });
  }

  update(lune: ILune): Observable<EntityResponseType> {
    return this.http.put<ILune>(this.resourceUrl, lune, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILune>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILune[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
