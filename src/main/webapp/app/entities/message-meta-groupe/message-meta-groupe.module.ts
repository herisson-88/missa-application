import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { MessageMetaGroupeComponent } from './message-meta-groupe.component';
import { MessageMetaGroupeDetailComponent } from './message-meta-groupe-detail.component';
import { MessageMetaGroupeUpdateComponent } from './message-meta-groupe-update.component';
import { MessageMetaGroupeDeleteDialogComponent } from './message-meta-groupe-delete-dialog.component';
import { messageMetaGroupeRoute } from './message-meta-groupe.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(messageMetaGroupeRoute)],
  declarations: [
    MessageMetaGroupeComponent,
    MessageMetaGroupeDetailComponent,
    MessageMetaGroupeUpdateComponent,
    MessageMetaGroupeDeleteDialogComponent
  ],
  entryComponents: [MessageMetaGroupeDeleteDialogComponent]
})
export class MissaMessageMetaGroupeModule {}
