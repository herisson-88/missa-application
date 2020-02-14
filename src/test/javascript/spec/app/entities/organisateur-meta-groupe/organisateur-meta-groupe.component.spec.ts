import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MissaTestModule } from '../../../test.module';
import { OrganisateurMetaGroupeComponent } from 'app/entities/organisateur-meta-groupe/organisateur-meta-groupe.component';
import { OrganisateurMetaGroupeService } from 'app/entities/organisateur-meta-groupe/organisateur-meta-groupe.service';
import { OrganisateurMetaGroupe } from 'app/shared/model/organisateur-meta-groupe.model';

describe('Component Tests', () => {
  describe('OrganisateurMetaGroupe Management Component', () => {
    let comp: OrganisateurMetaGroupeComponent;
    let fixture: ComponentFixture<OrganisateurMetaGroupeComponent>;
    let service: OrganisateurMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [OrganisateurMetaGroupeComponent]
      })
        .overrideTemplate(OrganisateurMetaGroupeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrganisateurMetaGroupeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrganisateurMetaGroupeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrganisateurMetaGroupe(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.organisateurMetaGroupes && comp.organisateurMetaGroupes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
