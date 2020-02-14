import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { MissaUserComponent } from './missa-user.component';
import { MissaUserDetailComponent } from './missa-user-detail.component';
import { MissaUserUpdateComponent } from './missa-user-update.component';
import { MissaUserDeleteDialogComponent } from './missa-user-delete-dialog.component';
import { missaUserRoute } from './missa-user.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(missaUserRoute)],
  declarations: [MissaUserComponent, MissaUserDetailComponent, MissaUserUpdateComponent, MissaUserDeleteDialogComponent],
  entryComponents: [MissaUserDeleteDialogComponent]
})
export class MissaMissaUserModule {}
