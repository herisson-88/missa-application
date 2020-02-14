import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPartageMetaGroupe } from 'app/shared/model/partage-meta-groupe.model';

@Component({
  selector: 'jhi-partage-meta-groupe-detail',
  templateUrl: './partage-meta-groupe-detail.component.html'
})
export class PartageMetaGroupeDetailComponent implements OnInit {
  partageMetaGroupe: IPartageMetaGroupe | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partageMetaGroupe }) => (this.partageMetaGroupe = partageMetaGroupe));
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
