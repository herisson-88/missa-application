import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrganisateurMetaGroupe, OrganisateurMetaGroupe } from 'app/shared/model/organisateur-meta-groupe.model';
import { OrganisateurMetaGroupeService } from './organisateur-meta-groupe.service';
import { OrganisateurMetaGroupeComponent } from './organisateur-meta-groupe.component';
import { OrganisateurMetaGroupeDetailComponent } from './organisateur-meta-groupe-detail.component';
import { OrganisateurMetaGroupeUpdateComponent } from './organisateur-meta-groupe-update.component';

@Injectable({ providedIn: 'root' })
export class OrganisateurMetaGroupeResolve implements Resolve<IOrganisateurMetaGroupe> {
  constructor(private service: OrganisateurMetaGroupeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganisateurMetaGroupe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((organisateurMetaGroupe: HttpResponse<OrganisateurMetaGroupe>) => {
          if (organisateurMetaGroupe.body) {
            return of(organisateurMetaGroupe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrganisateurMetaGroupe());
  }
}

export const organisateurMetaGroupeRoute: Routes = [
  {
    path: '',
    component: OrganisateurMetaGroupeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrganisateurMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrganisateurMetaGroupeDetailComponent,
    resolve: {
      organisateurMetaGroupe: OrganisateurMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrganisateurMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrganisateurMetaGroupeUpdateComponent,
    resolve: {
      organisateurMetaGroupe: OrganisateurMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrganisateurMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrganisateurMetaGroupeUpdateComponent,
    resolve: {
      organisateurMetaGroupe: OrganisateurMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrganisateurMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  }
];
