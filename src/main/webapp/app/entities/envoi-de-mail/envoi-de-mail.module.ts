import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MissaSharedModule } from 'app/shared/shared.module';
import { EnvoiDeMailComponent } from './envoi-de-mail.component';
import { EnvoiDeMailDetailComponent } from './envoi-de-mail-detail.component';
import { EnvoiDeMailUpdateComponent } from './envoi-de-mail-update.component';
import { EnvoiDeMailDeleteDialogComponent } from './envoi-de-mail-delete-dialog.component';
import { envoiDeMailRoute } from './envoi-de-mail.route';

@NgModule({
  imports: [MissaSharedModule, RouterModule.forChild(envoiDeMailRoute)],
  declarations: [EnvoiDeMailComponent, EnvoiDeMailDetailComponent, EnvoiDeMailUpdateComponent, EnvoiDeMailDeleteDialogComponent],
  entryComponents: [EnvoiDeMailDeleteDialogComponent]
})
export class MissaEnvoiDeMailModule {}
