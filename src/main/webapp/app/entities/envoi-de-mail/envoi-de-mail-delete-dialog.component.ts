import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnvoiDeMail } from 'app/shared/model/envoi-de-mail.model';
import { EnvoiDeMailService } from './envoi-de-mail.service';

@Component({
  templateUrl: './envoi-de-mail-delete-dialog.component.html'
})
export class EnvoiDeMailDeleteDialogComponent {
  envoiDeMail?: IEnvoiDeMail;

  constructor(
    protected envoiDeMailService: EnvoiDeMailService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.envoiDeMailService.delete(id).subscribe(() => {
      this.eventManager.broadcast('envoiDeMailListModification');
      this.activeModal.close();
    });
  }
}
