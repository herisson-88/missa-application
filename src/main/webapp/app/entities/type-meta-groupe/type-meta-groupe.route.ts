import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeMetaGroupe, TypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';
import { TypeMetaGroupeService } from './type-meta-groupe.service';
import { TypeMetaGroupeComponent } from './type-meta-groupe.component';
import { TypeMetaGroupeDetailComponent } from './type-meta-groupe-detail.component';
import { TypeMetaGroupeUpdateComponent } from './type-meta-groupe-update.component';

@Injectable({ providedIn: 'root' })
export class TypeMetaGroupeResolve implements Resolve<ITypeMetaGroupe> {
  constructor(private service: TypeMetaGroupeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeMetaGroupe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeMetaGroupe: HttpResponse<TypeMetaGroupe>) => {
          if (typeMetaGroupe.body) {
            return of(typeMetaGroupe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeMetaGroupe());
  }
}

export const typeMetaGroupeRoute: Routes = [
  {
    path: '',
    component: TypeMetaGroupeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TypeMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeMetaGroupeDetailComponent,
    resolve: {
      typeMetaGroupe: TypeMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TypeMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeMetaGroupeUpdateComponent,
    resolve: {
      typeMetaGroupe: TypeMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TypeMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeMetaGroupeUpdateComponent,
    resolve: {
      typeMetaGroupe: TypeMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TypeMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  }
];
