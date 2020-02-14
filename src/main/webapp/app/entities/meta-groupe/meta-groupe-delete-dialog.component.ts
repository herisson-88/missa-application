import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MetaGroupeService } from './meta-groupe.service';

@Component({
  templateUrl: './meta-groupe-delete-dialog.component.html'
})
export class MetaGroupeDeleteDialogComponent {
  metaGroupe?: IMetaGroupe;

  constructor(
    protected metaGroupeService: MetaGroupeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.metaGroupeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('metaGroupeListModification');
      this.activeModal.close();
    });
  }
}
