import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';

@Component({
  selector: 'jhi-type-meta-groupe-detail',
  templateUrl: './type-meta-groupe-detail.component.html'
})
export class TypeMetaGroupeDetailComponent implements OnInit {
  typeMetaGroupe: ITypeMetaGroupe | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeMetaGroupe }) => (this.typeMetaGroupe = typeMetaGroupe));
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
