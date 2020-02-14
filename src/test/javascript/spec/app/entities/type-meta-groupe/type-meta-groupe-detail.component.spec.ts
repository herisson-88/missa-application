import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MissaTestModule } from '../../../test.module';
import { TypeMetaGroupeDetailComponent } from 'app/entities/type-meta-groupe/type-meta-groupe-detail.component';
import { TypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';

describe('Component Tests', () => {
  describe('TypeMetaGroupe Management Detail Component', () => {
    let comp: TypeMetaGroupeDetailComponent;
    let fixture: ComponentFixture<TypeMetaGroupeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ typeMetaGroupe: new TypeMetaGroupe(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [TypeMetaGroupeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeMetaGroupeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeMetaGroupeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load typeMetaGroupe on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeMetaGroupe).toEqual(jasmine.objectContaining({ id: 123 }));
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
