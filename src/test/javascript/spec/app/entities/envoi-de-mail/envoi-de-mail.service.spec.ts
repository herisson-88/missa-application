import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EnvoiDeMailService } from 'app/entities/envoi-de-mail/envoi-de-mail.service';
import { IEnvoiDeMail, EnvoiDeMail } from 'app/shared/model/envoi-de-mail.model';

describe('Service Tests', () => {
  describe('EnvoiDeMail Service', () => {
    let injector: TestBed;
    let service: EnvoiDeMailService;
    let httpMock: HttpTestingController;
    let elemDefault: IEnvoiDeMail;
    let expectedResult: IEnvoiDeMail | IEnvoiDeMail[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EnvoiDeMailService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EnvoiDeMail(0, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateEnvoi: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EnvoiDeMail', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateEnvoi: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEnvoi: currentDate
          },
          returnedFromService
        );

        service.create(new EnvoiDeMail()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EnvoiDeMail', () => {
        const returnedFromService = Object.assign(
          {
            dateEnvoi: currentDate.format(DATE_TIME_FORMAT),
            titre: 'BBBBBB',
            adresseMail: 'BBBBBB',
            motSpirituel: 'BBBBBB',
            conseilTechnique: 'BBBBBB',
            nbDestinataire: 1,
            sended: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEnvoi: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EnvoiDeMail', () => {
        const returnedFromService = Object.assign(
          {
            dateEnvoi: currentDate.format(DATE_TIME_FORMAT),
            titre: 'BBBBBB',
            adresseMail: 'BBBBBB',
            motSpirituel: 'BBBBBB',
            conseilTechnique: 'BBBBBB',
            nbDestinataire: 1,
            sended: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEnvoi: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EnvoiDeMail', () => {
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
