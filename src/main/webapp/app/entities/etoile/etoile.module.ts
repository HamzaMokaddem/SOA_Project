import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GalaxySharedModule } from 'app/shared/shared.module';
import { EtoileComponent } from './etoile.component';
import { EtoileDetailComponent } from './etoile-detail.component';
import { EtoileUpdateComponent } from './etoile-update.component';
import { EtoileDeleteDialogComponent } from './etoile-delete-dialog.component';
import { etoileRoute } from './etoile.route';

@NgModule({
  imports: [GalaxySharedModule, RouterModule.forChild(etoileRoute)],
  declarations: [EtoileComponent, EtoileDetailComponent, EtoileUpdateComponent, EtoileDeleteDialogComponent],
  entryComponents: [EtoileDeleteDialogComponent],
})
export class GalaxyEtoileModule {}
