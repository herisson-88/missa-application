import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { ConnaissanceDetailComponent } from 'app/entities/connaissance/connaissance-detail.component';
import { Connaissance } from 'app/shared/model/connaissance.model';

describe('Component Tests', () => {
  describe('Connaissance Management Detail Component', () => {
    let comp: ConnaissanceDetailComponent;
    let fixture: ComponentFixture<ConnaissanceDetailComponent>;
    const route = ({ data: of({ connaissance: new Connaissance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [ConnaissanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ConnaissanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConnaissanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load connaissance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.connaissance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
