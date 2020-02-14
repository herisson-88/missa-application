import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILienDocMetaGroupe, LienDocMetaGroupe } from 'app/shared/model/lien-doc-meta-groupe.model';
import { LienDocMetaGroupeService } from './lien-doc-meta-groupe.service';
import { LienDocMetaGroupeComponent } from './lien-doc-meta-groupe.component';
import { LienDocMetaGroupeDetailComponent } from './lien-doc-meta-groupe-detail.component';
import { LienDocMetaGroupeUpdateComponent } from './lien-doc-meta-groupe-update.component';

@Injectable({ providedIn: 'root' })
export class LienDocMetaGroupeResolve implements Resolve<ILienDocMetaGroupe> {
  constructor(private service: LienDocMetaGroupeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILienDocMetaGroupe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lienDocMetaGroupe: HttpResponse<LienDocMetaGroupe>) => {
          if (lienDocMetaGroupe.body) {
            return of(lienDocMetaGroupe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LienDocMetaGroupe());
  }
}

export const lienDocMetaGroupeRoute: Routes = [
  {
    path: '',
    component: LienDocMetaGroupeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'LienDocMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LienDocMetaGroupeDetailComponent,
    resolve: {
      lienDocMetaGroupe: LienDocMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LienDocMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LienDocMetaGroupeUpdateComponent,
    resolve: {
      lienDocMetaGroupe: LienDocMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LienDocMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LienDocMetaGroupeUpdateComponent,
    resolve: {
      lienDocMetaGroupe: LienDocMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LienDocMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  }
];
