import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { TypeMetaGroupeComponent } from './type-meta-groupe.component';
import { TypeMetaGroupeDetailComponent } from './type-meta-groupe-detail.component';
import { TypeMetaGroupeUpdateComponent } from './type-meta-groupe-update.component';
import { TypeMetaGroupeDeleteDialogComponent } from './type-meta-groupe-delete-dialog.component';
import { typeMetaGroupeRoute } from './type-meta-groupe.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(typeMetaGroupeRoute)],
  declarations: [
    TypeMetaGroupeComponent,
    TypeMetaGroupeDetailComponent,
    TypeMetaGroupeUpdateComponent,
    TypeMetaGroupeDeleteDialogComponent
  ],
  entryComponents: [TypeMetaGroupeDeleteDialogComponent]
})
export class MissaTypeMetaGroupeModule {}
