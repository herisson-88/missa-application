import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { TypeMetaGroupeUpdateComponent } from 'app/entities/type-meta-groupe/type-meta-groupe-update.component';
import { TypeMetaGroupeService } from 'app/entities/type-meta-groupe/type-meta-groupe.service';
import { TypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';

describe('Component Tests', () => {
  describe('TypeMetaGroupe Management Update Component', () => {
    let comp: TypeMetaGroupeUpdateComponent;
    let fixture: ComponentFixture<TypeMetaGroupeUpdateComponent>;
    let service: TypeMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [TypeMetaGroupeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeMetaGroupeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeMetaGroupeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeMetaGroupeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeMetaGroupe(123);
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
        const entity = new TypeMetaGroupe();
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
