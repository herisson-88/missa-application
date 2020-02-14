import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { ReseauUpdateComponent } from 'app/entities/reseau/reseau-update.component';
import { ReseauService } from 'app/entities/reseau/reseau.service';
import { Reseau } from 'app/shared/model/reseau.model';

describe('Component Tests', () => {
  describe('Reseau Management Update Component', () => {
    let comp: ReseauUpdateComponent;
    let fixture: ComponentFixture<ReseauUpdateComponent>;
    let service: ReseauService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [ReseauUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReseauUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReseauUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReseauService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Reseau(123);
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
        const entity = new Reseau();
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
