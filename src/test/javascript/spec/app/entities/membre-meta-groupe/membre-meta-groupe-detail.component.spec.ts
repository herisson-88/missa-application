import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MissaTestModule } from '../../../test.module';
import { MembreMetaGroupeDetailComponent } from 'app/entities/membre-meta-groupe/membre-meta-groupe-detail.component';
import { MembreMetaGroupe } from 'app/shared/model/membre-meta-groupe.model';

describe('Component Tests', () => {
  describe('MembreMetaGroupe Management Detail Component', () => {
    let comp: MembreMetaGroupeDetailComponent;
    let fixture: ComponentFixture<MembreMetaGroupeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ membreMetaGroupe: new MembreMetaGroupe(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [MembreMetaGroupeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MembreMetaGroupeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MembreMetaGroupeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load membreMetaGroupe on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.membreMetaGroupe).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
