import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMembreMetaGroupe, MembreMetaGroupe } from 'app/shared/model/membre-meta-groupe.model';
import { MembreMetaGroupeService } from './membre-meta-groupe.service';
import { MembreMetaGroupeComponent } from './membre-meta-groupe.component';
import { MembreMetaGroupeDetailComponent } from './membre-meta-groupe-detail.component';
import { MembreMetaGroupeUpdateComponent } from './membre-meta-groupe-update.component';

@Injectable({ providedIn: 'root' })
export class MembreMetaGroupeResolve implements Resolve<IMembreMetaGroupe> {
  constructor(private service: MembreMetaGroupeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMembreMetaGroupe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((membreMetaGroupe: HttpResponse<MembreMetaGroupe>) => {
          if (membreMetaGroupe.body) {
            return of(membreMetaGroupe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MembreMetaGroupe());
  }
}

export const membreMetaGroupeRoute: Routes = [
  {
    path: '',
    component: MembreMetaGroupeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'MembreMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MembreMetaGroupeDetailComponent,
    resolve: {
      membreMetaGroupe: MembreMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MembreMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MembreMetaGroupeUpdateComponent,
    resolve: {
      membreMetaGroupe: MembreMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MembreMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MembreMetaGroupeUpdateComponent,
    resolve: {
      membreMetaGroupe: MembreMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MembreMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  }
];
