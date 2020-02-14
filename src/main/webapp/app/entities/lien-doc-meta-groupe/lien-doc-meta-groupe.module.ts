import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { LienDocMetaGroupeComponent } from './lien-doc-meta-groupe.component';
import { LienDocMetaGroupeDetailComponent } from './lien-doc-meta-groupe-detail.component';
import { LienDocMetaGroupeUpdateComponent } from './lien-doc-meta-groupe-update.component';
import { LienDocMetaGroupeDeleteDialogComponent } from './lien-doc-meta-groupe-delete-dialog.component';
import { lienDocMetaGroupeRoute } from './lien-doc-meta-groupe.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(lienDocMetaGroupeRoute)],
  declarations: [
    LienDocMetaGroupeComponent,
    LienDocMetaGroupeDetailComponent,
    LienDocMetaGroupeUpdateComponent,
    LienDocMetaGroupeDeleteDialogComponent
  ],
  entryComponents: [LienDocMetaGroupeDeleteDialogComponent]
})
export class MissaLienDocMetaGroupeModule {}
