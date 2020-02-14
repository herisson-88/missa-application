import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MissaTestModule } from '../../../test.module';
import { EnvoiDeMailDetailComponent } from 'app/entities/envoi-de-mail/envoi-de-mail-detail.component';
import { EnvoiDeMail } from 'app/shared/model/envoi-de-mail.model';

describe('Component Tests', () => {
  describe('EnvoiDeMail Management Detail Component', () => {
    let comp: EnvoiDeMailDetailComponent;
    let fixture: ComponentFixture<EnvoiDeMailDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ envoiDeMail: new EnvoiDeMail(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [EnvoiDeMailDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EnvoiDeMailDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnvoiDeMailDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load envoiDeMail on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.envoiDeMail).toEqual(jasmine.objectContaining({ id: 123 }));
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
