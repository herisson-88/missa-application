import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILienDocMetaGroupe } from 'app/shared/model/lien-doc-meta-groupe.model';
import { LienDocMetaGroupeService } from './lien-doc-meta-groupe.service';

@Component({
  templateUrl: './lien-doc-meta-groupe-delete-dialog.component.html'
})
export class LienDocMetaGroupeDeleteDialogComponent {
  lienDocMetaGroupe?: ILienDocMetaGroupe;

  constructor(
    protected lienDocMetaGroupeService: LienDocMetaGroupeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lienDocMetaGroupeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lienDocMetaGroupeListModification');
      this.activeModal.close();
    });
  }
}
