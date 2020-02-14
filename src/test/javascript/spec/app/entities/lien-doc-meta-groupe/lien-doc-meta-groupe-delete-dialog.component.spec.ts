import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MissaTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { LienDocMetaGroupeDeleteDialogComponent } from 'app/entities/lien-doc-meta-groupe/lien-doc-meta-groupe-delete-dialog.component';
import { LienDocMetaGroupeService } from 'app/entities/lien-doc-meta-groupe/lien-doc-meta-groupe.service';

describe('Component Tests', () => {
  describe('LienDocMetaGroupe Management Delete Component', () => {
    let comp: LienDocMetaGroupeDeleteDialogComponent;
    let fixture: ComponentFixture<LienDocMetaGroupeDeleteDialogComponent>;
    let service: LienDocMetaGroupeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [LienDocMetaGroupeDeleteDialogComponent]
      })
        .overrideTemplate(LienDocMetaGroupeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LienDocMetaGroupeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LienDocMetaGroupeService);
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
