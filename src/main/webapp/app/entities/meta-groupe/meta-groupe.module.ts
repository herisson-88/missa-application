import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { MetaGroupeComponent } from './meta-groupe.component';
import { MetaGroupeDetailComponent } from './meta-groupe-detail.component';
import { MetaGroupeUpdateComponent } from './meta-groupe-update.component';
import { MetaGroupeDeleteDialogComponent } from './meta-groupe-delete-dialog.component';
import { metaGroupeRoute } from './meta-groupe.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(metaGroupeRoute)],
  declarations: [MetaGroupeComponent, MetaGroupeDetailComponent, MetaGroupeUpdateComponent, MetaGroupeDeleteDialogComponent],
  entryComponents: [MetaGroupeDeleteDialogComponent]
})
export class MissaMetaGroupeModule {}
