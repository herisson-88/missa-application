import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MissaTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { PartageMetaGroupeDeleteDialogComponent } from 'app/entities/partage-meta-groupe/partage-meta-groupe-delete-dialog.component';
import { PartageMetaGroupeService } from 'app/entities/partage-meta-groupe/partage-meta-groupe.service';

describe('Component Tests', () => {
  describe('PartageMetaGroupe Management Delete Component', () => {
    let comp: PartageMetaGroupeDeleteDialogComponent;
    let fixture: ComponentFixture<PartageMetaGroupeDeleteDialogComponent>;
    let service: PartageMetaGroupeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [PartageMetaGroupeDeleteDialogComponent]
      })
        .overrideTemplate(PartageMetaGroupeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartageMetaGroupeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartageMetaGroupeService);
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
