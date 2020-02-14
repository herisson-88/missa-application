import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { ConnaissanceUpdateComponent } from 'app/entities/connaissance/connaissance-update.component';
import { ConnaissanceService } from 'app/entities/connaissance/connaissance.service';
import { Connaissance } from 'app/shared/model/connaissance.model';

describe('Component Tests', () => {
  describe('Connaissance Management Update Component', () => {
    let comp: ConnaissanceUpdateComponent;
    let fixture: ComponentFixture<ConnaissanceUpdateComponent>;
    let service: ConnaissanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [ConnaissanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ConnaissanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConnaissanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConnaissanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Connaissance(123);
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
        const entity = new Connaissance();
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
