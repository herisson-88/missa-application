import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { MembreMetaGroupeComponent } from './membre-meta-groupe.component';
import { MembreMetaGroupeDetailComponent } from './membre-meta-groupe-detail.component';
import { MembreMetaGroupeUpdateComponent } from './membre-meta-groupe-update.component';
import { MembreMetaGroupeDeleteDialogComponent } from './membre-meta-groupe-delete-dialog.component';
import { membreMetaGroupeRoute } from './membre-meta-groupe.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(membreMetaGroupeRoute)],
  declarations: [
    MembreMetaGroupeComponent,
    MembreMetaGroupeDetailComponent,
    MembreMetaGroupeUpdateComponent,
    MembreMetaGroupeDeleteDialogComponent
  ],
  entryComponents: [MembreMetaGroupeDeleteDialogComponent]
})
export class MissaMembreMetaGroupeModule {}
