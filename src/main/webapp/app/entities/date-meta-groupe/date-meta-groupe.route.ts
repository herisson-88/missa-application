import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDateMetaGroupe, DateMetaGroupe } from 'app/shared/model/date-meta-groupe.model';
import { DateMetaGroupeService } from './date-meta-groupe.service';
import { DateMetaGroupeComponent } from './date-meta-groupe.component';
import { DateMetaGroupeDetailComponent } from './date-meta-groupe-detail.component';
import { DateMetaGroupeUpdateComponent } from './date-meta-groupe-update.component';

@Injectable({ providedIn: 'root' })
export class DateMetaGroupeResolve implements Resolve<IDateMetaGroupe> {
  constructor(private service: DateMetaGroupeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDateMetaGroupe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dateMetaGroupe: HttpResponse<DateMetaGroupe>) => {
          if (dateMetaGroupe.body) {
            return of(dateMetaGroupe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DateMetaGroupe());
  }
}

export const dateMetaGroupeRoute: Routes = [
  {
    path: '',
    component: DateMetaGroupeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DateMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DateMetaGroupeDetailComponent,
    resolve: {
      dateMetaGroupe: DateMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DateMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DateMetaGroupeUpdateComponent,
    resolve: {
      dateMetaGroupe: DateMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DateMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DateMetaGroupeUpdateComponent,
    resolve: {
      dateMetaGroupe: DateMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DateMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  }
];
