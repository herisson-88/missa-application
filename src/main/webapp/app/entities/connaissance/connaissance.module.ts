import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { ConnaissanceComponent } from './connaissance.component';
import { ConnaissanceDetailComponent } from './connaissance-detail.component';
import { ConnaissanceUpdateComponent } from './connaissance-update.component';
import { ConnaissanceDeleteDialogComponent } from './connaissance-delete-dialog.component';
import { connaissanceRoute } from './connaissance.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(connaissanceRoute)],
  declarations: [ConnaissanceComponent, ConnaissanceDetailComponent, ConnaissanceUpdateComponent, ConnaissanceDeleteDialogComponent],
  entryComponents: [ConnaissanceDeleteDialogComponent]
})
export class MissaConnaissanceModule {}
