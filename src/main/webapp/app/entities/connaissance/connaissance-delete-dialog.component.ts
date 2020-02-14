import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConnaissance } from 'app/shared/model/connaissance.model';
import { ConnaissanceService } from './connaissance.service';

@Component({
  templateUrl: './connaissance-delete-dialog.component.html'
})
export class ConnaissanceDeleteDialogComponent {
  connaissance?: IConnaissance;

  constructor(
    protected connaissanceService: ConnaissanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.connaissanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('connaissanceListModification');
      this.activeModal.close();
    });
  }
}
