import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MissaTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { MessageMetaGroupeDeleteDialogComponent } from 'app/entities/message-meta-groupe/message-meta-groupe-delete-dialog.component';
import { MessageMetaGroupeService } from 'app/entities/message-meta-groupe/message-meta-groupe.service';

describe('Component Tests', () => {
  describe('MessageMetaGroupe Management Delete Component', () => {
    let comp: MessageMetaGroupeDeleteDialogComponent;
    let fixture: ComponentFixture<MessageMetaGroupeDeleteDialogComponent>;
    let service: MessageMetaGroupeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [MessageMetaGroupeDeleteDialogComponent]
      })
        .overrideTemplate(MessageMetaGroupeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MessageMetaGroupeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MessageMetaGroupeService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
