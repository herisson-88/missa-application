import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { MissaTestModule } from '../../../test.module';
import { PartageMetaGroupeComponent } from 'app/entities/partage-meta-groupe/partage-meta-groupe.component';
import { PartageMetaGroupeService } from 'app/entities/partage-meta-groupe/partage-meta-groupe.service';
import { PartageMetaGroupe } from 'app/shared/model/partage-meta-groupe.model';

describe('Component Tests', () => {
  describe('PartageMetaGroupe Management Component', () => {
    let comp: PartageMetaGroupeComponent;
    let fixture: ComponentFixture<PartageMetaGroupeComponent>;
    let service: PartageMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [PartageMetaGroupeComponent],
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
        .overrideTemplate(PartageMetaGroupeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartageMetaGroupeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartageMetaGroupeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartageMetaGroupe(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partageMetaGroupes && comp.partageMetaGroupes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartageMetaGroupe(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partageMetaGroupes && comp.partageMetaGroupes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
