import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MissaTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { MembreMetaGroupeDeleteDialogComponent } from 'app/entities/membre-meta-groupe/membre-meta-groupe-delete-dialog.component';
import { MembreMetaGroupeService } from 'app/entities/membre-meta-groupe/membre-meta-groupe.service';

describe('Component Tests', () => {
  describe('MembreMetaGroupe Management Delete Component', () => {
    let comp: MembreMetaGroupeDeleteDialogComponent;
    let fixture: ComponentFixture<MembreMetaGroupeDeleteDialogComponent>;
    let service: MembreMetaGroupeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [MembreMetaGroupeDeleteDialogComponent]
      })
        .overrideTemplate(MembreMetaGroupeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MembreMetaGroupeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MembreMetaGroupeService);
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
