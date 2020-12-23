import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanet, Planet } from 'app/shared/model/planet.model';
import { PlanetService } from './planet.service';
import { PlanetComponent } from './planet.component';
import { PlanetDetailComponent } from './planet-detail.component';
import { PlanetUpdateComponent } from './planet-update.component';

@Injectable({ providedIn: 'root' })
export class PlanetResolve implements Resolve<IPlanet> {
  constructor(private service: PlanetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planet: HttpResponse<Planet>) => {
          if (planet.body) {
            return of(planet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Planet());
  }
}

export const planetRoute: Routes = [
  {
    path: '',
    component: PlanetComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'galaxyApp.planet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanetDetailComponent,
    resolve: {
      planet: PlanetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'galaxyApp.planet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanetUpdateComponent,
    resolve: {
      planet: PlanetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'galaxyApp.planet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanetUpdateComponent,
    resolve: {
      planet: PlanetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'galaxyApp.planet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
