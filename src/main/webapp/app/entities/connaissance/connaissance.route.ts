import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConnaissance, Connaissance } from 'app/shared/model/connaissance.model';
import { ConnaissanceService } from './connaissance.service';
import { ConnaissanceComponent } from './connaissance.component';
import { ConnaissanceDetailComponent } from './connaissance-detail.component';
import { ConnaissanceUpdateComponent } from './connaissance-update.component';

@Injectable({ providedIn: 'root' })
export class ConnaissanceResolve implements Resolve<IConnaissance> {
  constructor(private service: ConnaissanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConnaissance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((connaissance: HttpResponse<Connaissance>) => {
          if (connaissance.body) {
            return of(connaissance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Connaissance());
  }
}

export const connaissanceRoute: Routes = [
  {
    path: '',
    component: ConnaissanceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Connaissances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConnaissanceDetailComponent,
    resolve: {
      connaissance: ConnaissanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Connaissances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConnaissanceUpdateComponent,
    resolve: {
      connaissance: ConnaissanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Connaissances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ConnaissanceUpdateComponent,
    resolve: {
      connaissance: ConnaissanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Connaissances'
    },
    canActivate: [UserRouteAccessService]
  }
];
