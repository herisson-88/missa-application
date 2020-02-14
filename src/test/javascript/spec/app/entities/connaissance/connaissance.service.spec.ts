import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ConnaissanceService } from 'app/entities/connaissance/connaissance.service';
import { IConnaissance, Connaissance } from 'app/shared/model/connaissance.model';

describe('Service Tests', () => {
  describe('Connaissance Service', () => {
    let injector: TestBed;
    let service: ConnaissanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IConnaissance;
    let expectedResult: IConnaissance | IConnaissance[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConnaissanceService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Connaissance(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Connaissance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Connaissance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Connaissance', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            mail: 'BBBBBB',
            idFaceBook: 'BBBBBB',
            parraine: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Connaissance', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            mail: 'BBBBBB',
            idFaceBook: 'BBBBBB',
            parraine: true
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

      it('should delete a Connaissance', () => {
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
