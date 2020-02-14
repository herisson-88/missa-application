import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartageMetaGroupe } from 'app/shared/model/partage-meta-groupe.model';
import { PartageMetaGroupeService } from './partage-meta-groupe.service';

@Component({
  templateUrl: './partage-meta-groupe-delete-dialog.component.html'
})
export class PartageMetaGroupeDeleteDialogComponent {
  partageMetaGroupe?: IPartageMetaGroupe;

  constructor(
    protected partageMetaGroupeService: PartageMetaGroupeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partageMetaGroupeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partageMetaGroupeListModification');
      this.activeModal.close();
    });
  }
}
