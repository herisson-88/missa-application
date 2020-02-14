import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILieuMetaGroupe } from 'app/shared/model/lieu-meta-groupe.model';

@Component({
  selector: 'jhi-lieu-meta-groupe-detail',
  templateUrl: './lieu-meta-groupe-detail.component.html'
})
export class LieuMetaGroupeDetailComponent implements OnInit {
  lieuMetaGroupe: ILieuMetaGroupe | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lieuMetaGroupe }) => (this.lieuMetaGroupe = lieuMetaGroupe));
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
