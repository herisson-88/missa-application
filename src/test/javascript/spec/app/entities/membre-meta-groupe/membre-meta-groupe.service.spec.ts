import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MembreMetaGroupeService } from 'app/entities/membre-meta-groupe/membre-meta-groupe.service';
import { IMembreMetaGroupe, MembreMetaGroupe } from 'app/shared/model/membre-meta-groupe.model';

describe('Service Tests', () => {
  describe('MembreMetaGroupe Service', () => {
    let injector: TestBed;
    let service: MembreMetaGroupeService;
    let httpMock: HttpTestingController;
    let elemDefault: IMembreMetaGroupe;
    let expectedResult: IMembreMetaGroupe | IMembreMetaGroupe[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MembreMetaGroupeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MembreMetaGroupe(0, false, currentDate, false, 'AAAAAAA', 'AAAAAAA', false);
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

      it('should create a MembreMetaGroupe', () => {
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

        service.create(new MembreMetaGroupe()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MembreMetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            validated: true,
            dateValidation: currentDate.format(DATE_TIME_FORMAT),
            admin: true,
            presentation: 'BBBBBB',
            connaissance: 'BBBBBB',
            mailingList: true
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

      it('should return a list of MembreMetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            validated: true,
            dateValidation: currentDate.format(DATE_TIME_FORMAT),
            admin: true,
            presentation: 'BBBBBB',
            connaissance: 'BBBBBB',
            mailingList: true
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

      it('should delete a MembreMetaGroupe', () => {
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
