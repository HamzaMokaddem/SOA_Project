import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'etoile',
        loadChildren: () => import('./etoile/etoile.module').then(m => m.GalaxyEtoileModule),
      },
      {
        path: 'planet',
        loadChildren: () => import('./planet/planet.module').then(m => m.GalaxyPlanetModule),
      },
      {
        path: 'lune',
        loadChildren: () => import('./lune/lune.module').then(m => m.GalaxyLuneModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GalaxyEntityModule {}
