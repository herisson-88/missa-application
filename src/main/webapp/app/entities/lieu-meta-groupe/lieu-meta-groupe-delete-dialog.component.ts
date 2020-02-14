import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILieuMetaGroupe } from 'app/shared/model/lieu-meta-groupe.model';
import { LieuMetaGroupeService } from './lieu-meta-groupe.service';

@Component({
  templateUrl: './lieu-meta-groupe-delete-dialog.component.html'
})
export class LieuMetaGroupeDeleteDialogComponent {
  lieuMetaGroupe?: ILieuMetaGroupe;

  constructor(
    protected lieuMetaGroupeService: LieuMetaGroupeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lieuMetaGroupeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lieuMetaGroupeListModification');
      this.activeModal.close();
    });
  }
}
