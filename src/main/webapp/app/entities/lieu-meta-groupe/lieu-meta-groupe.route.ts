import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILieuMetaGroupe, LieuMetaGroupe } from 'app/shared/model/lieu-meta-groupe.model';
import { LieuMetaGroupeService } from './lieu-meta-groupe.service';
import { LieuMetaGroupeComponent } from './lieu-meta-groupe.component';
import { LieuMetaGroupeDetailComponent } from './lieu-meta-groupe-detail.component';
import { LieuMetaGroupeUpdateComponent } from './lieu-meta-groupe-update.component';

@Injectable({ providedIn: 'root' })
export class LieuMetaGroupeResolve implements Resolve<ILieuMetaGroupe> {
  constructor(private service: LieuMetaGroupeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILieuMetaGroupe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lieuMetaGroupe: HttpResponse<LieuMetaGroupe>) => {
          if (lieuMetaGroupe.body) {
            return of(lieuMetaGroupe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LieuMetaGroupe());
  }
}

export const lieuMetaGroupeRoute: Routes = [
  {
    path: '',
    component: LieuMetaGroupeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LieuMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LieuMetaGroupeDetailComponent,
    resolve: {
      lieuMetaGroupe: LieuMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LieuMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LieuMetaGroupeUpdateComponent,
    resolve: {
      lieuMetaGroupe: LieuMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LieuMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LieuMetaGroupeUpdateComponent,
    resolve: {
      lieuMetaGroupe: LieuMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LieuMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  }
];
