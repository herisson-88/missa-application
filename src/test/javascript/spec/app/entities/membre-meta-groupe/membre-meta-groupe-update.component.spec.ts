import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { MembreMetaGroupeUpdateComponent } from 'app/entities/membre-meta-groupe/membre-meta-groupe-update.component';
import { MembreMetaGroupeService } from 'app/entities/membre-meta-groupe/membre-meta-groupe.service';
import { MembreMetaGroupe } from 'app/shared/model/membre-meta-groupe.model';

describe('Component Tests', () => {
  describe('MembreMetaGroupe Management Update Component', () => {
    let comp: MembreMetaGroupeUpdateComponent;
    let fixture: ComponentFixture<MembreMetaGroupeUpdateComponent>;
    let service: MembreMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [MembreMetaGroupeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MembreMetaGroupeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MembreMetaGroupeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MembreMetaGroupeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MembreMetaGroupe(123);
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
        const entity = new MembreMetaGroupe();
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
