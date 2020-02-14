import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMissaUser } from 'app/shared/model/missa-user.model';
import { MissaUserService } from './missa-user.service';

@Component({
  templateUrl: './missa-user-delete-dialog.component.html'
})
export class MissaUserDeleteDialogComponent {
  missaUser?: IMissaUser;

  constructor(protected missaUserService: MissaUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.missaUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('missaUserListModification');
      this.activeModal.close();
    });
  }
}
