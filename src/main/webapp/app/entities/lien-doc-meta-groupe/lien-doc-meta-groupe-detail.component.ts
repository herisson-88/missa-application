import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILienDocMetaGroupe } from 'app/shared/model/lien-doc-meta-groupe.model';

@Component({
  selector: 'jhi-lien-doc-meta-groupe-detail',
  templateUrl: './lien-doc-meta-groupe-detail.component.html'
})
export class LienDocMetaGroupeDetailComponent implements OnInit {
  lienDocMetaGroupe: ILienDocMetaGroupe | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lienDocMetaGroupe }) => (this.lienDocMetaGroupe = lienDocMetaGroupe));
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
