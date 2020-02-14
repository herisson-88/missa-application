import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReseau } from 'app/shared/model/reseau.model';
import { ReseauService } from './reseau.service';

@Component({
  templateUrl: './reseau-delete-dialog.component.html'
})
export class ReseauDeleteDialogComponent {
  reseau?: IReseau;

  constructor(protected reseauService: ReseauService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reseauService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reseauListModification');
      this.activeModal.close();
    });
  }
}
