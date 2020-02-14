import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMessageMetaGroupe } from 'app/shared/model/message-meta-groupe.model';
import { MessageMetaGroupeService } from './message-meta-groupe.service';

@Component({
  templateUrl: './message-meta-groupe-delete-dialog.component.html'
})
export class MessageMetaGroupeDeleteDialogComponent {
  messageMetaGroupe?: IMessageMetaGroupe;

  constructor(
    protected messageMetaGroupeService: MessageMetaGroupeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.messageMetaGroupeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('messageMetaGroupeListModification');
      this.activeModal.close();
    });
  }
}
