import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';
import { TypeMetaGroupeService } from './type-meta-groupe.service';

@Component({
  templateUrl: './type-meta-groupe-delete-dialog.component.html'
})
export class TypeMetaGroupeDeleteDialogComponent {
  typeMetaGroupe?: ITypeMetaGroupe;

  constructor(
    protected typeMetaGroupeService: TypeMetaGroupeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeMetaGroupeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeMetaGroupeListModification');
      this.activeModal.close();
    });
  }
}
