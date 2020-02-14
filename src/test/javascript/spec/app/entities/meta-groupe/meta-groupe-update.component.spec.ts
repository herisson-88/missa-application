import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { MetaGroupeUpdateComponent } from 'app/entities/meta-groupe/meta-groupe-update.component';
import { MetaGroupeService } from 'app/entities/meta-groupe/meta-groupe.service';
import { MetaGroupe } from 'app/shared/model/meta-groupe.model';

describe('Component Tests', () => {
  describe('MetaGroupe Management Update Component', () => {
    let comp: MetaGroupeUpdateComponent;
    let fixture: ComponentFixture<MetaGroupeUpdateComponent>;
    let service: MetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [MetaGroupeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MetaGroupeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MetaGroupeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MetaGroupeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MetaGroupe(123);
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
        const entity = new MetaGroupe();
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
