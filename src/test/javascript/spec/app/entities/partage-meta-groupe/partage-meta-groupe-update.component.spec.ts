import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { PartageMetaGroupeUpdateComponent } from 'app/entities/partage-meta-groupe/partage-meta-groupe-update.component';
import { PartageMetaGroupeService } from 'app/entities/partage-meta-groupe/partage-meta-groupe.service';
import { PartageMetaGroupe } from 'app/shared/model/partage-meta-groupe.model';

describe('Component Tests', () => {
  describe('PartageMetaGroupe Management Update Component', () => {
    let comp: PartageMetaGroupeUpdateComponent;
    let fixture: ComponentFixture<PartageMetaGroupeUpdateComponent>;
    let service: PartageMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [PartageMetaGroupeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PartageMetaGroupeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartageMetaGroupeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartageMetaGroupeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartageMetaGroupe(123);
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
        const entity = new PartageMetaGroupe();
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
