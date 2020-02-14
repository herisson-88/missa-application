import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MissaTestModule } from '../../../test.module';
import { MissaUserDetailComponent } from 'app/entities/missa-user/missa-user-detail.component';
import { MissaUser } from 'app/shared/model/missa-user.model';

describe('Component Tests', () => {
  describe('MissaUser Management Detail Component', () => {
    let comp: MissaUserDetailComponent;
    let fixture: ComponentFixture<MissaUserDetailComponent>;
    const route = ({ data: of({ missaUser: new MissaUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MissaTestModule],
        declarations: [MissaUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MissaUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MissaUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load missaUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.missaUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
