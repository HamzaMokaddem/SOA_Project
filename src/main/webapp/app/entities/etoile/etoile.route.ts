import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEtoile, Etoile } from 'app/shared/model/etoile.model';
import { EtoileService } from './etoile.service';
import { EtoileComponent } from './etoile.component';
import { EtoileDetailComponent } from './etoile-detail.component';
import { EtoileUpdateComponent } from './etoile-update.component';

@Injectable({ providedIn: 'root' })
export class EtoileResolve implements Resolve<IEtoile> {
  constructor(private service: EtoileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtoile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((etoile: HttpResponse<Etoile>) => {
          if (etoile.body) {
            return of(etoile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Etoile());
  }
}

export const etoileRoute: Routes = [
  {
    path: '',
    component: EtoileComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'galaxyApp.etoile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EtoileDetailComponent,
    resolve: {
      etoile: EtoileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'galaxyApp.etoile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EtoileUpdateComponent,
    resolve: {
      etoile: EtoileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'galaxyApp.etoile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EtoileUpdateComponent,
    resolve: {
      etoile: EtoileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'galaxyApp.etoile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
