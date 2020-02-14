import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { DateMetaGroupeComponent } from './date-meta-groupe.component';
import { DateMetaGroupeDetailComponent } from './date-meta-groupe-detail.component';
import { DateMetaGroupeUpdateComponent } from './date-meta-groupe-update.component';
import { DateMetaGroupeDeleteDialogComponent } from './date-meta-groupe-delete-dialog.component';
import { dateMetaGroupeRoute } from './date-meta-groupe.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(dateMetaGroupeRoute)],
  declarations: [
    DateMetaGroupeComponent,
    DateMetaGroupeDetailComponent,
    DateMetaGroupeUpdateComponent,
    DateMetaGroupeDeleteDialogComponent
  ],
  entryComponents: [DateMetaGroupeDeleteDialogComponent]
})
export class MissaDateMetaGroupeModule {}
