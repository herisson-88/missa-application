import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MissaTestModule } from '../../../test.module';
import { MetaGroupeDetailComponent } from 'app/entities/meta-groupe/meta-groupe-detail.component';
import { MetaGroupe } from 'app/shared/model/meta-groupe.model';

describe('Component Tests', () => {
  describe('MetaGroupe Management Detail Component', () => {
    let comp: MetaGroupeDetailComponent;
    let fixture: ComponentFixture<MetaGroupeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ metaGroupe: new MetaGroupe(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [MetaGroupeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MetaGroupeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MetaGroupeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load metaGroupe on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.metaGroupe).toEqual(jasmine.objectContaining({ id: 123 }));
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
