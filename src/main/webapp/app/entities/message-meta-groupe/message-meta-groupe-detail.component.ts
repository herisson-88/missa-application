import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMessageMetaGroupe } from 'app/shared/model/message-meta-groupe.model';

@Component({
  selector: 'jhi-message-meta-groupe-detail',
  templateUrl: './message-meta-groupe-detail.component.html'
})
export class MessageMetaGroupeDetailComponent implements OnInit {
  messageMetaGroupe: IMessageMetaGroupe | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ messageMetaGroupe }) => (this.messageMetaGroupe = messageMetaGroupe));
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
