import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GalaxySharedModule } from 'app/shared/shared.module';
import { PlanetComponent } from './planet.component';
import { PlanetDetailComponent } from './planet-detail.component';
import { PlanetUpdateComponent } from './planet-update.component';
import { PlanetDeleteDialogComponent } from './planet-delete-dialog.component';
import { planetRoute } from './planet.route';

@NgModule({
  imports: [GalaxySharedModule, RouterModule.forChild(planetRoute)],
  declarations: [PlanetComponent, PlanetDetailComponent, PlanetUpdateComponent, PlanetDeleteDialogComponent],
  entryComponents: [PlanetDeleteDialogComponent],
})
export class GalaxyPlanetModule {}
