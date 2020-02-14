import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReseau, Reseau } from 'app/shared/model/reseau.model';
import { ReseauService } from './reseau.service';
import { ReseauComponent } from './reseau.component';
import { ReseauDetailComponent } from './reseau-detail.component';
import { ReseauUpdateComponent } from './reseau-update.component';

@Injectable({ providedIn: 'root' })
export class ReseauResolve implements Resolve<IReseau> {
  constructor(private service: ReseauService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReseau> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reseau: HttpResponse<Reseau>) => {
          if (reseau.body) {
            return of(reseau.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Reseau());
  }
}

export const reseauRoute: Routes = [
  {
    path: '',
    component: ReseauComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Reseaus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReseauDetailComponent,
    resolve: {
      reseau: ReseauResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Reseaus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReseauUpdateComponent,
    resolve: {
      reseau: ReseauResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Reseaus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReseauUpdateComponent,
    resolve: {
      reseau: ReseauResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Reseaus'
    },
    canActivate: [UserRouteAccessService]
  }
];
