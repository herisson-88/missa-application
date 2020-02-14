import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMissaUser, MissaUser } from 'app/shared/model/missa-user.model';
import { MissaUserService } from './missa-user.service';
import { MissaUserComponent } from './missa-user.component';
import { MissaUserDetailComponent } from './missa-user-detail.component';
import { MissaUserUpdateComponent } from './missa-user-update.component';

@Injectable({ providedIn: 'root' })
export class MissaUserResolve implements Resolve<IMissaUser> {
  constructor(private service: MissaUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMissaUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((missaUser: HttpResponse<MissaUser>) => {
          if (missaUser.body) {
            return of(missaUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MissaUser());
  }
}

export const missaUserRoute: Routes = [
  {
    path: '',
    component: MissaUserComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MissaUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MissaUserDetailComponent,
    resolve: {
      missaUser: MissaUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MissaUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MissaUserUpdateComponent,
    resolve: {
      missaUser: MissaUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MissaUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MissaUserUpdateComponent,
    resolve: {
      missaUser: MissaUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MissaUsers'
    },
    canActivate: [UserRouteAccessService]
  }
];
