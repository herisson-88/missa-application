import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMetaGroupe, MetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MetaGroupeService } from './meta-groupe.service';
import { MetaGroupeComponent } from './meta-groupe.component';
import { MetaGroupeDetailComponent } from './meta-groupe-detail.component';
import { MetaGroupeUpdateComponent } from './meta-groupe-update.component';

@Injectable({ providedIn: 'root' })
export class MetaGroupeResolve implements Resolve<IMetaGroupe> {
  constructor(private service: MetaGroupeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMetaGroupe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((metaGroupe: HttpResponse<MetaGroupe>) => {
          if (metaGroupe.body) {
            return of(metaGroupe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MetaGroupe());
  }
}

export const metaGroupeRoute: Routes = [
  {
    path: '',
    component: MetaGroupeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'MetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MetaGroupeDetailComponent,
    resolve: {
      metaGroupe: MetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MetaGroupeUpdateComponent,
    resolve: {
      metaGroupe: MetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MetaGroupeUpdateComponent,
    resolve: {
      metaGroupe: MetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  }
];
