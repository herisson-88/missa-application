import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDateMetaGroupe } from 'app/shared/model/date-meta-groupe.model';
import { DateMetaGroupeService } from './date-meta-groupe.service';

@Component({
  templateUrl: './date-meta-groupe-delete-dialog.component.html'
})
export class DateMetaGroupeDeleteDialogComponent {
  dateMetaGroupe?: IDateMetaGroupe;

  constructor(
    protected dateMetaGroupeService: DateMetaGroupeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dateMetaGroupeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dateMetaGroupeListModification');
      this.activeModal.close();
    });
  }
}
