import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LieuMetaGroupeService } from 'app/entities/lieu-meta-groupe/lieu-meta-groupe.service';
import { ILieuMetaGroupe, LieuMetaGroupe } from 'app/shared/model/lieu-meta-groupe.model';

describe('Service Tests', () => {
  describe('LieuMetaGroupe Service', () => {
    let injector: TestBed;
    let service: LieuMetaGroupeService;
    let httpMock: HttpTestingController;
    let elemDefault: ILieuMetaGroupe;
    let expectedResult: ILieuMetaGroupe | ILieuMetaGroupe[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LieuMetaGroupeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new LieuMetaGroupe(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LieuMetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new LieuMetaGroupe()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LieuMetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            adresse: 'BBBBBB',
            codePostal: 'BBBBBB',
            ville: 'BBBBBB',
            lat: 1,
            lon: 1,
            detail: 'BBBBBB',
            departementGroupe: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LieuMetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            adresse: 'BBBBBB',
            codePostal: 'BBBBBB',
            ville: 'BBBBBB',
            lat: 1,
            lon: 1,
            detail: 'BBBBBB',
            departementGroupe: 'BBBBBB'
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

      it('should delete a LieuMetaGroupe', () => {
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
