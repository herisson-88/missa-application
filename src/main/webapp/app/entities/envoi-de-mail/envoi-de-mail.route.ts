import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEnvoiDeMail, EnvoiDeMail } from 'app/shared/model/envoi-de-mail.model';
import { EnvoiDeMailService } from './envoi-de-mail.service';
import { EnvoiDeMailComponent } from './envoi-de-mail.component';
import { EnvoiDeMailDetailComponent } from './envoi-de-mail-detail.component';
import { EnvoiDeMailUpdateComponent } from './envoi-de-mail-update.component';

@Injectable({ providedIn: 'root' })
export class EnvoiDeMailResolve implements Resolve<IEnvoiDeMail> {
  constructor(private service: EnvoiDeMailService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnvoiDeMail> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((envoiDeMail: HttpResponse<EnvoiDeMail>) => {
          if (envoiDeMail.body) {
            return of(envoiDeMail.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EnvoiDeMail());
  }
}

export const envoiDeMailRoute: Routes = [
  {
    path: '',
    component: EnvoiDeMailComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'EnvoiDeMails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EnvoiDeMailDetailComponent,
    resolve: {
      envoiDeMail: EnvoiDeMailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EnvoiDeMails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EnvoiDeMailUpdateComponent,
    resolve: {
      envoiDeMail: EnvoiDeMailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EnvoiDeMails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EnvoiDeMailUpdateComponent,
    resolve: {
      envoiDeMail: EnvoiDeMailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EnvoiDeMails'
    },
    canActivate: [UserRouteAccessService]
  }
];
