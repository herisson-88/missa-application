import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LienDocMetaGroupeService } from 'app/entities/lien-doc-meta-groupe/lien-doc-meta-groupe.service';
import { ILienDocMetaGroupe, LienDocMetaGroupe } from 'app/shared/model/lien-doc-meta-groupe.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

describe('Service Tests', () => {
  describe('LienDocMetaGroupe Service', () => {
    let injector: TestBed;
    let service: LienDocMetaGroupeService;
    let httpMock: HttpTestingController;
    let elemDefault: ILienDocMetaGroupe;
    let expectedResult: ILienDocMetaGroupe | ILienDocMetaGroupe[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LienDocMetaGroupeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new LienDocMetaGroupe(
        0,
        'AAAAAAA',
        TypeDoc.DOC,
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LienDocMetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new LienDocMetaGroupe()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LienDocMetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            typeDuDoc: 'BBBBBB',
            icone: 'BBBBBB',
            image: 'BBBBBB',
            doc: 'BBBBBB',
            detail: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LienDocMetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            typeDuDoc: 'BBBBBB',
            icone: 'BBBBBB',
            image: 'BBBBBB',
            doc: 'BBBBBB',
            detail: 'BBBBBB'
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

      it('should delete a LienDocMetaGroupe', () => {
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
