import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { OrganisateurMetaGroupeUpdateComponent } from 'app/entities/organisateur-meta-groupe/organisateur-meta-groupe-update.component';
import { OrganisateurMetaGroupeService } from 'app/entities/organisateur-meta-groupe/organisateur-meta-groupe.service';
import { OrganisateurMetaGroupe } from 'app/shared/model/organisateur-meta-groupe.model';

describe('Component Tests', () => {
  describe('OrganisateurMetaGroupe Management Update Component', () => {
    let comp: OrganisateurMetaGroupeUpdateComponent;
    let fixture: ComponentFixture<OrganisateurMetaGroupeUpdateComponent>;
    let service: OrganisateurMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [OrganisateurMetaGroupeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrganisateurMetaGroupeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrganisateurMetaGroupeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrganisateurMetaGroupeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrganisateurMetaGroupe(123);
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
        const entity = new OrganisateurMetaGroupe();
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
