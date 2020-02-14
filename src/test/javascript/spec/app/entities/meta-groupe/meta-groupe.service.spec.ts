import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MetaGroupeService } from 'app/entities/meta-groupe/meta-groupe.service';
import { IMetaGroupe, MetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MembreDiffusion } from 'app/shared/model/enumerations/membre-diffusion.model';
import { Visibilite } from 'app/shared/model/enumerations/visibilite.model';

describe('Service Tests', () => {
  describe('MetaGroupe Service', () => {
    let injector: TestBed;
    let service: MetaGroupeService;
    let httpMock: HttpTestingController;
    let elemDefault: IMetaGroupe;
    let expectedResult: IMetaGroupe | IMetaGroupe[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MetaGroupeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MetaGroupe(
        0,
        'AAAAAAA',
        false,
        MembreDiffusion.DIFFUSION_UP,
        Visibilite.EXISTENCE_PUBLIC,
        false,
        false,
        false,
        false,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateValidation: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateValidation: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateValidation: currentDate
          },
          returnedFromService
        );

        service.create(new MetaGroupe()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            autoriteValidation: true,
            membreParent: 'BBBBBB',
            visibilite: 'BBBBBB',
            droitEnvoiDeMail: true,
            demandeDiffusionVerticale: true,
            messagerieModeree: true,
            groupeFinal: true,
            dateValidation: currentDate.format(DATE_TIME_FORMAT),
            detail: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateValidation: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            autoriteValidation: true,
            membreParent: 'BBBBBB',
            visibilite: 'BBBBBB',
            droitEnvoiDeMail: true,
            demandeDiffusionVerticale: true,
            messagerieModeree: true,
            groupeFinal: true,
            dateValidation: currentDate.format(DATE_TIME_FORMAT),
            detail: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateValidation: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MetaGroupe', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
