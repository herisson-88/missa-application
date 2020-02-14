import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { LieuMetaGroupeUpdateComponent } from 'app/entities/lieu-meta-groupe/lieu-meta-groupe-update.component';
import { LieuMetaGroupeService } from 'app/entities/lieu-meta-groupe/lieu-meta-groupe.service';
import { LieuMetaGroupe } from 'app/shared/model/lieu-meta-groupe.model';

describe('Component Tests', () => {
  describe('LieuMetaGroupe Management Update Component', () => {
    let comp: LieuMetaGroupeUpdateComponent;
    let fixture: ComponentFixture<LieuMetaGroupeUpdateComponent>;
    let service: LieuMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [LieuMetaGroupeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LieuMetaGroupeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LieuMetaGroupeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LieuMetaGroupeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LieuMetaGroupe(123);
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
        const entity = new LieuMetaGroupe();
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
