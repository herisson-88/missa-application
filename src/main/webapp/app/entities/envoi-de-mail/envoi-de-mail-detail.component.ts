import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEnvoiDeMail } from 'app/shared/model/envoi-de-mail.model';

@Component({
  selector: 'jhi-envoi-de-mail-detail',
  templateUrl: './envoi-de-mail-detail.component.html'
})
export class EnvoiDeMailDetailComponent implements OnInit {
  envoiDeMail: IEnvoiDeMail | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ envoiDeMail }) => (this.envoiDeMail = envoiDeMail));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
