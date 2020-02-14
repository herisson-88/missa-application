import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'reseau',
        loadChildren: () => import('./reseau/reseau.module').then(m => m.MissaReseauModule)
      },
      {
        path: 'type-meta-groupe',
        loadChildren: () => import('./type-meta-groupe/type-meta-groupe.module').then(m => m.MissaTypeMetaGroupeModule)
      },
      {
        path: 'connaissance',
        loadChildren: () => import('./connaissance/connaissance.module').then(m => m.MissaConnaissanceModule)
      },
      {
        path: 'meta-groupe',
        loadChildren: () => import('./meta-groupe/meta-groupe.module').then(m => m.MissaMetaGroupeModule)
      },
      {
        path: 'partage-meta-groupe',
        loadChildren: () => import('./partage-meta-groupe/partage-meta-groupe.module').then(m => m.MissaPartageMetaGroupeModule)
      },
      {
        path: 'envoi-de-mail',
        loadChildren: () => import('./envoi-de-mail/envoi-de-mail.module').then(m => m.MissaEnvoiDeMailModule)
      },
      {
        path: 'lien-doc-meta-groupe',
        loadChildren: () => import('./lien-doc-meta-groupe/lien-doc-meta-groupe.module').then(m => m.MissaLienDocMetaGroupeModule)
      },
      {
        path: 'lieu-meta-groupe',
        loadChildren: () => import('./lieu-meta-groupe/lieu-meta-groupe.module').then(m => m.MissaLieuMetaGroupeModule)
      },
      {
        path: 'message-meta-groupe',
        loadChildren: () => import('./message-meta-groupe/message-meta-groupe.module').then(m => m.MissaMessageMetaGroupeModule)
      },
      {
        path: 'date-meta-groupe',
        loadChildren: () => import('./date-meta-groupe/date-meta-groupe.module').then(m => m.MissaDateMetaGroupeModule)
      },
      {
        path: 'membre-meta-groupe',
        loadChildren: () => import('./membre-meta-groupe/membre-meta-groupe.module').then(m => m.MissaMembreMetaGroupeModule)
      },
      {
        path: 'missa-user',
        loadChildren: () => import('./missa-user/missa-user.module').then(m => m.MissaMissaUserModule)
      },
      {
        path: 'organisateur-meta-groupe',
        loadChildren: () =>
          import('./organisateur-meta-groupe/organisateur-meta-groupe.module').then(m => m.MissaOrganisateurMetaGroupeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MissaEntityModule {}
