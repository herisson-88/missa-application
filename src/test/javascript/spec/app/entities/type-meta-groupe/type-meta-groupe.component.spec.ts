import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MissaTestModule } from '../../../test.module';
import { TypeMetaGroupeComponent } from 'app/entities/type-meta-groupe/type-meta-groupe.component';
import { TypeMetaGroupeService } from 'app/entities/type-meta-groupe/type-meta-groupe.service';
import { TypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';

describe('Component Tests', () => {
  describe('TypeMetaGroupe Management Component', () => {
    let comp: TypeMetaGroupeComponent;
    let fixture: ComponentFixture<TypeMetaGroupeComponent>;
    let service: TypeMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [TypeMetaGroupeComponent]
      })
        .overrideTemplate(TypeMetaGroupeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeMetaGroupeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeMetaGroupeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TypeMetaGroupe(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.typeMetaGroupes && comp.typeMetaGroupes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
