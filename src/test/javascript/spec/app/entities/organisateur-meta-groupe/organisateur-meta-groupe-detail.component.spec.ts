import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MissaTestModule } from '../../../test.module';
import { OrganisateurMetaGroupeDetailComponent } from 'app/entities/organisateur-meta-groupe/organisateur-meta-groupe-detail.component';
import { OrganisateurMetaGroupe } from 'app/shared/model/organisateur-meta-groupe.model';

describe('Component Tests', () => {
  describe('OrganisateurMetaGroupe Management Detail Component', () => {
    let comp: OrganisateurMetaGroupeDetailComponent;
    let fixture: ComponentFixture<OrganisateurMetaGroupeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ organisateurMetaGroupe: new OrganisateurMetaGroupe(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [OrganisateurMetaGroupeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrganisateurMetaGroupeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrganisateurMetaGroupeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load organisateurMetaGroupe on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.organisateurMetaGroupe).toEqual(jasmine.objectContaining({ id: 123 }));
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
