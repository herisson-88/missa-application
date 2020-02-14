import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MissaSharedModule } from 'app/shared/shared.module';
import { MissaCoreModule } from 'app/core/core.module';
import { MissaAppRoutingModule } from './app-routing.module';
import { MissaHomeModule } from './home/home.module';
import { MissaEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    MissaSharedModule,
    MissaCoreModule,
    MissaHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MissaEntityModule,
    MissaAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class MissaAppModule {}
