import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { PartageMetaGroupeComponent } from './partage-meta-groupe.component';
import { PartageMetaGroupeDetailComponent } from './partage-meta-groupe-detail.component';
import { PartageMetaGroupeUpdateComponent } from './partage-meta-groupe-update.component';
import { PartageMetaGroupeDeleteDialogComponent } from './partage-meta-groupe-delete-dialog.component';
import { partageMetaGroupeRoute } from './partage-meta-groupe.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(partageMetaGroupeRoute)],
  declarations: [
    PartageMetaGroupeComponent,
    PartageMetaGroupeDetailComponent,
    PartageMetaGroupeUpdateComponent,
    PartageMetaGroupeDeleteDialogComponent
  ],
  entryComponents: [PartageMetaGroupeDeleteDialogComponent]
})
export class MissaPartageMetaGroupeModule {}
