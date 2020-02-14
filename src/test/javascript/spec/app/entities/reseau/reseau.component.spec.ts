import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MissaTestModule } from '../../../test.module';
import { ReseauComponent } from 'app/entities/reseau/reseau.component';
import { ReseauService } from 'app/entities/reseau/reseau.service';
import { Reseau } from 'app/shared/model/reseau.model';

describe('Component Tests', () => {
  describe('Reseau Management Component', () => {
    let comp: ReseauComponent;
    let fixture: ComponentFixture<ReseauComponent>;
    let service: ReseauService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [ReseauComponent]
      })
        .overrideTemplate(ReseauComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReseauComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReseauService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Reseau(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.reseaus && comp.reseaus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
