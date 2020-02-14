import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MissaTestModule } from '../../../test.module';
import { DateMetaGroupeComponent } from 'app/entities/date-meta-groupe/date-meta-groupe.component';
import { DateMetaGroupeService } from 'app/entities/date-meta-groupe/date-meta-groupe.service';
import { DateMetaGroupe } from 'app/shared/model/date-meta-groupe.model';

describe('Component Tests', () => {
  describe('DateMetaGroupe Management Component', () => {
    let comp: DateMetaGroupeComponent;
    let fixture: ComponentFixture<DateMetaGroupeComponent>;
    let service: DateMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [DateMetaGroupeComponent]
      })
        .overrideTemplate(DateMetaGroupeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DateMetaGroupeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DateMetaGroupeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DateMetaGroupe(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dateMetaGroupes && comp.dateMetaGroupes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
