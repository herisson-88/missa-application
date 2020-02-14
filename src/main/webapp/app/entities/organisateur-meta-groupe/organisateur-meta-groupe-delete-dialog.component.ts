import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrganisateurMetaGroupe } from 'app/shared/model/organisateur-meta-groupe.model';
import { OrganisateurMetaGroupeService } from './organisateur-meta-groupe.service';

@Component({
  templateUrl: './organisateur-meta-groupe-delete-dialog.component.html'
})
export class OrganisateurMetaGroupeDeleteDialogComponent {
  organisateurMetaGroupe?: IOrganisateurMetaGroupe;

  constructor(
    protected organisateurMetaGroupeService: OrganisateurMetaGroupeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organisateurMetaGroupeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('organisateurMetaGroupeListModification');
      this.activeModal.close();
    });
  }
}
