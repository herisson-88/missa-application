import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { MissaTestModule } from '../../../test.module';
import { LienDocMetaGroupeComponent } from 'app/entities/lien-doc-meta-groupe/lien-doc-meta-groupe.component';
import { LienDocMetaGroupeService } from 'app/entities/lien-doc-meta-groupe/lien-doc-meta-groupe.service';
import { LienDocMetaGroupe } from 'app/shared/model/lien-doc-meta-groupe.model';

describe('Component Tests', () => {
  describe('LienDocMetaGroupe Management Component', () => {
    let comp: LienDocMetaGroupeComponent;
    let fixture: ComponentFixture<LienDocMetaGroupeComponent>;
    let service: LienDocMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [LienDocMetaGroupeComponent],
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
        .overrideTemplate(LienDocMetaGroupeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LienDocMetaGroupeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LienDocMetaGroupeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LienDocMetaGroupe(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lienDocMetaGroupes && comp.lienDocMetaGroupes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LienDocMetaGroupe(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lienDocMetaGroupes && comp.lienDocMetaGroupes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
