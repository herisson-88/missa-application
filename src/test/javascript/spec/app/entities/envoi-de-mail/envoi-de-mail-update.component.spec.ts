import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { EnvoiDeMailUpdateComponent } from 'app/entities/envoi-de-mail/envoi-de-mail-update.component';
import { EnvoiDeMailService } from 'app/entities/envoi-de-mail/envoi-de-mail.service';
import { EnvoiDeMail } from 'app/shared/model/envoi-de-mail.model';

describe('Component Tests', () => {
  describe('EnvoiDeMail Management Update Component', () => {
    let comp: EnvoiDeMailUpdateComponent;
    let fixture: ComponentFixture<EnvoiDeMailUpdateComponent>;
    let service: EnvoiDeMailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [EnvoiDeMailUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EnvoiDeMailUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnvoiDeMailUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnvoiDeMailService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EnvoiDeMail(123);
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
        const entity = new EnvoiDeMail();
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
