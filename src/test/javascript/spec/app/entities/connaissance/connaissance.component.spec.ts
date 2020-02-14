import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MissaTestModule } from '../../../test.module';
import { ConnaissanceComponent } from 'app/entities/connaissance/connaissance.component';
import { ConnaissanceService } from 'app/entities/connaissance/connaissance.service';
import { Connaissance } from 'app/shared/model/connaissance.model';

describe('Component Tests', () => {
  describe('Connaissance Management Component', () => {
    let comp: ConnaissanceComponent;
    let fixture: ComponentFixture<ConnaissanceComponent>;
    let service: ConnaissanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [ConnaissanceComponent]
      })
        .overrideTemplate(ConnaissanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConnaissanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConnaissanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Connaissance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.connaissances && comp.connaissances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
