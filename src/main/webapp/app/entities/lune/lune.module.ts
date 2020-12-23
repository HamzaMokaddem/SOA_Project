import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GalaxySharedModule } from 'app/shared/shared.module';
import { LuneComponent } from './lune.component';
import { LuneDetailComponent } from './lune-detail.component';
import { LuneUpdateComponent } from './lune-update.component';
import { LuneDeleteDialogComponent } from './lune-delete-dialog.component';
import { luneRoute } from './lune.route';

@NgModule({
  imports: [GalaxySharedModule, RouterModule.forChild(luneRoute)],
  declarations: [LuneComponent, LuneDetailComponent, LuneUpdateComponent, LuneDeleteDialogComponent],
  entryComponents: [LuneDeleteDialogComponent],
})
export class GalaxyLuneModule {}
