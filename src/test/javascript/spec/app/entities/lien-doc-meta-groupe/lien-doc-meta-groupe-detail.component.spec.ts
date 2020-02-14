import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MissaTestModule } from '../../../test.module';
import { LienDocMetaGroupeDetailComponent } from 'app/entities/lien-doc-meta-groupe/lien-doc-meta-groupe-detail.component';
import { LienDocMetaGroupe } from 'app/shared/model/lien-doc-meta-groupe.model';

describe('Component Tests', () => {
  describe('LienDocMetaGroupe Management Detail Component', () => {
    let comp: LienDocMetaGroupeDetailComponent;
    let fixture: ComponentFixture<LienDocMetaGroupeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ lienDocMetaGroupe: new LienDocMetaGroupe(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [LienDocMetaGroupeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LienDocMetaGroupeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LienDocMetaGroupeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load lienDocMetaGroupe on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lienDocMetaGroupe).toEqual(jasmine.objectContaining({ id: 123 }));
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
