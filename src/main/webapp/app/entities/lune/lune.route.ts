import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILune, Lune } from 'app/shared/model/lune.model';
import { LuneService } from './lune.service';
import { LuneComponent } from './lune.component';
import { LuneDetailComponent } from './lune-detail.component';
import { LuneUpdateComponent } from './lune-update.component';

@Injectable({ providedIn: 'root' })
export class LuneResolve implements Resolve<ILune> {
  constructor(private service: LuneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILune> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lune: HttpResponse<Lune>) => {
          if (lune.body) {
            return of(lune.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Lune());
  }
}

export const luneRoute: Routes = [
  {
    path: '',
    component: LuneComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'galaxyApp.lune.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LuneDetailComponent,
    resolve: {
      lune: LuneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'galaxyApp.lune.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LuneUpdateComponent,
    resolve: {
      lune: LuneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'galaxyApp.lune.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LuneUpdateComponent,
    resolve: {
      lune: LuneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'galaxyApp.lune.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
