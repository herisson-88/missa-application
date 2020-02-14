import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMembreMetaGroupe } from 'app/shared/model/membre-meta-groupe.model';
import { MembreMetaGroupeService } from './membre-meta-groupe.service';

@Component({
  templateUrl: './membre-meta-groupe-delete-dialog.component.html'
})
export class MembreMetaGroupeDeleteDialogComponent {
  membreMetaGroupe?: IMembreMetaGroupe;

  constructor(
    protected membreMetaGroupeService: MembreMetaGroupeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.membreMetaGroupeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('membreMetaGroupeListModification');
      this.activeModal.close();
    });
  }
}
