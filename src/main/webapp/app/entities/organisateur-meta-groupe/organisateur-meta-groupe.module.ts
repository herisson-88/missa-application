import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { OrganisateurMetaGroupeComponent } from './organisateur-meta-groupe.component';
import { OrganisateurMetaGroupeDetailComponent } from './organisateur-meta-groupe-detail.component';
import { OrganisateurMetaGroupeUpdateComponent } from './organisateur-meta-groupe-update.component';
import { OrganisateurMetaGroupeDeleteDialogComponent } from './organisateur-meta-groupe-delete-dialog.component';
import { organisateurMetaGroupeRoute } from './organisateur-meta-groupe.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(organisateurMetaGroupeRoute)],
  declarations: [
    OrganisateurMetaGroupeComponent,
    OrganisateurMetaGroupeDetailComponent,
    OrganisateurMetaGroupeUpdateComponent,
    OrganisateurMetaGroupeDeleteDialogComponent
  ],
  entryComponents: [OrganisateurMetaGroupeDeleteDialogComponent]
})
export class MissaOrganisateurMetaGroupeModule {}
