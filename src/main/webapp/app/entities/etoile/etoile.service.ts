import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtoile } from 'app/shared/model/etoile.model';

type EntityResponseType = HttpResponse<IEtoile>;
type EntityArrayResponseType = HttpResponse<IEtoile[]>;

@Injectable({ providedIn: 'root' })
export class EtoileService {
  public resourceUrl = SERVER_API_URL + 'api/etoiles';

  constructor(protected http: HttpClient) {}

  create(etoile: IEtoile): Observable<EntityResponseType> {
    return this.http.post<IEtoile>(this.resourceUrl, etoile, { observe: 'response' });
  }

  update(etoile: IEtoile): Observable<EntityResponseType> {
    return this.http.put<IEtoile>(this.resourceUrl, etoile, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtoile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtoile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
