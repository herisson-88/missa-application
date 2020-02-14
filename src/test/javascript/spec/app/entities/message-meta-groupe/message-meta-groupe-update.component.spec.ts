import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { MessageMetaGroupeUpdateComponent } from 'app/entities/message-meta-groupe/message-meta-groupe-update.component';
import { MessageMetaGroupeService } from 'app/entities/message-meta-groupe/message-meta-groupe.service';
import { MessageMetaGroupe } from 'app/shared/model/message-meta-groupe.model';

describe('Component Tests', () => {
  describe('MessageMetaGroupe Management Update Component', () => {
    let comp: MessageMetaGroupeUpdateComponent;
    let fixture: ComponentFixture<MessageMetaGroupeUpdateComponent>;
    let service: MessageMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [MessageMetaGroupeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MessageMetaGroupeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MessageMetaGroupeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MessageMetaGroupeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MessageMetaGroupe(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MessageMetaGroupe();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
