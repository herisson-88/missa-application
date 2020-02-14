import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MissaTestModule } from '../../../test.module';
import { LieuMetaGroupeComponent } from 'app/entities/lieu-meta-groupe/lieu-meta-groupe.component';
import { LieuMetaGroupeService } from 'app/entities/lieu-meta-groupe/lieu-meta-groupe.service';
import { LieuMetaGroupe } from 'app/shared/model/lieu-meta-groupe.model';

describe('Component Tests', () => {
  describe('LieuMetaGroupe Management Component', () => {
    let comp: LieuMetaGroupeComponent;
    let fixture: ComponentFixture<LieuMetaGroupeComponent>;
    let service: LieuMetaGroupeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [LieuMetaGroupeComponent]
      })
        .overrideTemplate(LieuMetaGroupeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LieuMetaGroupeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LieuMetaGroupeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LieuMetaGroupe(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lieuMetaGroupes && comp.lieuMetaGroupes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
