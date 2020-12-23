import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILune } from 'app/shared/model/lune.model';
import { LuneService } from './lune.service';

@Component({
  templateUrl: './lune-delete-dialog.component.html',
})
export class LuneDeleteDialogComponent {
  lune?: ILune;

  constructor(protected luneService: LuneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.luneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('luneListModification');
      this.activeModal.close();
    });
  }
}
