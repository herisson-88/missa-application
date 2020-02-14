import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { DateMetaGroupeUpdateComponent } from 'app/entities/date-meta-groupe/date-meta-groupe-update.component';
import { DateMetaGroupeService } from 'app/entities/date-meta-groupe/date-meta-groupe.service';
import { DateMetaGroupe } from 'app/shared/model/date-meta-groupe.model';

describe('Component Tests', () => {
  describe('DateMetaGroupe Management Update Component', () => {
    let comp: DateMetaGroupeUpdateComponent;
    let fixture: ComponentFixture<DateMetaGroupeUpdateComponent>;
    let service: DateMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [DateMetaGroupeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DateMetaGroupeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DateMetaGroupeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DateMetaGroupeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DateMetaGroupe(123);
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
        const entity = new DateMetaGroupe();
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
