import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MissaUserService } from 'app/entities/missa-user/missa-user.service';
import { IMissaUser, MissaUser } from 'app/shared/model/missa-user.model';
import { EtatUser } from 'app/shared/model/enumerations/etat-user.model';

describe('Service Tests', () => {
  describe('MissaUser Service', () => {
    let injector: TestBed;
    let service: MissaUserService;
    let httpMock: HttpTestingController;
    let elemDefault: IMissaUser;
    let expectedResult: IMissaUser | IMissaUser[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MissaUserService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new MissaUser(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', EtatUser.CREE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MissaUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new MissaUser()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MissaUser', () => {
        const returnedFromService = Object.assign(
          {
            codePostal: 'BBBBBB',
            initiales: 'BBBBBB',
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            mail: 'BBBBBB',
            etat: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MissaUser', () => {
        const returnedFromService = Object.assign(
          {
            codePostal: 'BBBBBB',
            initiales: 'BBBBBB',
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            mail: 'BBBBBB',
            etat: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MissaUser', () => {
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
