import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanet } from 'app/shared/model/planet.model';
import { PlanetService } from './planet.service';

@Component({
  templateUrl: './planet-delete-dialog.component.html',
})
export class PlanetDeleteDialogComponent {
  planet?: IPlanet;

  constructor(protected planetService: PlanetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planetListModification');
      this.activeModal.close();
    });
  }
}
