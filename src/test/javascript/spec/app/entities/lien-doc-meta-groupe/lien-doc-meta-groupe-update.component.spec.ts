import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { LienDocMetaGroupeUpdateComponent } from 'app/entities/lien-doc-meta-groupe/lien-doc-meta-groupe-update.component';
import { LienDocMetaGroupeService } from 'app/entities/lien-doc-meta-groupe/lien-doc-meta-groupe.service';
import { LienDocMetaGroupe } from 'app/shared/model/lien-doc-meta-groupe.model';

describe('Component Tests', () => {
  describe('LienDocMetaGroupe Management Update Component', () => {
    let comp: LienDocMetaGroupeUpdateComponent;
    let fixture: ComponentFixture<LienDocMetaGroupeUpdateComponent>;
    let service: LienDocMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [LienDocMetaGroupeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LienDocMetaGroupeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LienDocMetaGroupeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LienDocMetaGroupeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LienDocMetaGroupe(123);
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
        const entity = new LienDocMetaGroupe();
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
