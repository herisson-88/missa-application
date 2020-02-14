import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartageMetaGroupe, PartageMetaGroupe } from 'app/shared/model/partage-meta-groupe.model';
import { PartageMetaGroupeService } from './partage-meta-groupe.service';
import { PartageMetaGroupeComponent } from './partage-meta-groupe.component';
import { PartageMetaGroupeDetailComponent } from './partage-meta-groupe-detail.component';
import { PartageMetaGroupeUpdateComponent } from './partage-meta-groupe-update.component';

@Injectable({ providedIn: 'root' })
export class PartageMetaGroupeResolve implements Resolve<IPartageMetaGroupe> {
  constructor(private service: PartageMetaGroupeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartageMetaGroupe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partageMetaGroupe: HttpResponse<PartageMetaGroupe>) => {
          if (partageMetaGroupe.body) {
            return of(partageMetaGroupe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartageMetaGroupe());
  }
}

export const partageMetaGroupeRoute: Routes = [
  {
    path: '',
    component: PartageMetaGroupeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'PartageMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PartageMetaGroupeDetailComponent,
    resolve: {
      partageMetaGroupe: PartageMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PartageMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PartageMetaGroupeUpdateComponent,
    resolve: {
      partageMetaGroupe: PartageMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PartageMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PartageMetaGroupeUpdateComponent,
    resolve: {
      partageMetaGroupe: PartageMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PartageMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  }
];
