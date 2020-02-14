import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { LieuMetaGroupeComponent } from './lieu-meta-groupe.component';
import { LieuMetaGroupeDetailComponent } from './lieu-meta-groupe-detail.component';
import { LieuMetaGroupeUpdateComponent } from './lieu-meta-groupe-update.component';
import { LieuMetaGroupeDeleteDialogComponent } from './lieu-meta-groupe-delete-dialog.component';
import { lieuMetaGroupeRoute } from './lieu-meta-groupe.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(lieuMetaGroupeRoute)],
  declarations: [
    LieuMetaGroupeComponent,
    LieuMetaGroupeDetailComponent,
    LieuMetaGroupeUpdateComponent,
    LieuMetaGroupeDeleteDialogComponent
  ],
  entryComponents: [LieuMetaGroupeDeleteDialogComponent]
})
export class MissaLieuMetaGroupeModule {}
