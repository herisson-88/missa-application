import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMembreMetaGroupe } from 'app/shared/model/membre-meta-groupe.model';

@Component({
  selector: 'jhi-membre-meta-groupe-detail',
  templateUrl: './membre-meta-groupe-detail.component.html'
})
export class MembreMetaGroupeDetailComponent implements OnInit {
  membreMetaGroupe: IMembreMetaGroupe | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membreMetaGroupe }) => (this.membreMetaGroupe = membreMetaGroupe));
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
