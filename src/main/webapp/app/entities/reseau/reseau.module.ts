import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { ReseauComponent } from './reseau.component';
import { ReseauDetailComponent } from './reseau-detail.component';
import { ReseauUpdateComponent } from './reseau-update.component';
import { ReseauDeleteDialogComponent } from './reseau-delete-dialog.component';
import { reseauRoute } from './reseau.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(reseauRoute)],
  declarations: [ReseauComponent, ReseauDetailComponent, ReseauUpdateComponent, ReseauDeleteDialogComponent],
  entryComponents: [ReseauDeleteDialogComponent]
})
export class MissaReseauModule {}
