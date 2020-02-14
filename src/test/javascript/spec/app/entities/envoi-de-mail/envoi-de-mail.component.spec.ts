import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { MissaTestModule } from '../../../test.module';
import { EnvoiDeMailComponent } from 'app/entities/envoi-de-mail/envoi-de-mail.component';
import { EnvoiDeMailService } from 'app/entities/envoi-de-mail/envoi-de-mail.service';
import { EnvoiDeMail } from 'app/shared/model/envoi-de-mail.model';

describe('Component Tests', () => {
  describe('EnvoiDeMail Management Component', () => {
    let comp: EnvoiDeMailComponent;
    let fixture: ComponentFixture<EnvoiDeMailComponent>;
    let service: EnvoiDeMailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [EnvoiDeMailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(EnvoiDeMailComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnvoiDeMailComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnvoiDeMailService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EnvoiDeMail(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.envoiDeMails && comp.envoiDeMails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EnvoiDeMail(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.envoiDeMails && comp.envoiDeMails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
