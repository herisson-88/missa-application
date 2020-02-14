import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TypeMetaGroupeService } from 'app/entities/type-meta-groupe/type-meta-groupe.service';
import { ITypeMetaGroupe, TypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';

describe('Service Tests', () => {
  describe('TypeMetaGroupe Service', () => {
    let injector: TestBed;
    let service: TypeMetaGroupeService;
    let httpMock: HttpTestingController;
    let elemDefault: ITypeMetaGroupe;
    let expectedResult: ITypeMetaGroupe | ITypeMetaGroupe[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TypeMetaGroupeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TypeMetaGroupe(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TypeMetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TypeMetaGroupe()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TypeMetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            typeDuGroupe: 'BBBBBB',
            iconeFa: 'BBBBBB',
            detail: 'BBBBBB',
            ordreMail: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TypeMetaGroupe', () => {
        const returnedFromService = Object.assign(
          {
            typeDuGroupe: 'BBBBBB',
            iconeFa: 'BBBBBB',
            detail: 'BBBBBB',
            ordreMail: 1
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

      it('should delete a TypeMetaGroupe', () => {
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
