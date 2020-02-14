import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMessageMetaGroupe, MessageMetaGroupe } from 'app/shared/model/message-meta-groupe.model';
import { MessageMetaGroupeService } from './message-meta-groupe.service';
import { MessageMetaGroupeComponent } from './message-meta-groupe.component';
import { MessageMetaGroupeDetailComponent } from './message-meta-groupe-detail.component';
import { MessageMetaGroupeUpdateComponent } from './message-meta-groupe-update.component';

@Injectable({ providedIn: 'root' })
export class MessageMetaGroupeResolve implements Resolve<IMessageMetaGroupe> {
  constructor(private service: MessageMetaGroupeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMessageMetaGroupe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((messageMetaGroupe: HttpResponse<MessageMetaGroupe>) => {
          if (messageMetaGroupe.body) {
            return of(messageMetaGroupe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MessageMetaGroupe());
  }
}

export const messageMetaGroupeRoute: Routes = [
  {
    path: '',
    component: MessageMetaGroupeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MessageMetaGroupeDetailComponent,
    resolve: {
      messageMetaGroupe: MessageMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MessageMetaGroupeUpdateComponent,
    resolve: {
      messageMetaGroupe: MessageMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MessageMetaGroupeUpdateComponent,
    resolve: {
      messageMetaGroupe: MessageMetaGroupeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageMetaGroupes'
    },
    canActivate: [UserRouteAccessService]
  }
];
