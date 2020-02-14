import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { MissaUserUpdateComponent } from 'app/entities/missa-user/missa-user-update.component';
import { MissaUserService } from 'app/entities/missa-user/missa-user.service';
import { MissaUser } from 'app/shared/model/missa-user.model';

describe('Component Tests', () => {
  describe('MissaUser Management Update Component', () => {
    let comp: MissaUserUpdateComponent;
    let fixture: ComponentFixture<MissaUserUpdateComponent>;
    let service: MissaUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [MissaUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MissaUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MissaUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MissaUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MissaUser(123);
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
        const entity = new MissaUser();
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
