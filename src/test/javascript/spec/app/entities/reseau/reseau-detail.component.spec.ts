import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { ReseauDetailComponent } from 'app/entities/reseau/reseau-detail.component';
import { Reseau } from 'app/shared/model/reseau.model';

describe('Component Tests', () => {
  describe('Reseau Management Detail Component', () => {
    let comp: ReseauDetailComponent;
    let fixture: ComponentFixture<ReseauDetailComponent>;
    const route = ({ data: of({ reseau: new Reseau(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [ReseauDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReseauDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReseauDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reseau on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reseau).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
