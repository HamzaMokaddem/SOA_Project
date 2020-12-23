import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtoile } from 'app/shared/model/etoile.model';
import { EtoileService } from './etoile.service';

@Component({
  templateUrl: './etoile-delete-dialog.component.html',
})
export class EtoileDeleteDialogComponent {
  etoile?: IEtoile;

  constructor(protected etoileService: EtoileService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etoileService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etoileListModification');
      this.activeModal.close();
    });
  }
}
