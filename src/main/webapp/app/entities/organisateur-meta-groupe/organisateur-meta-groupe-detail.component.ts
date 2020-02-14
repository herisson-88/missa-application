import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IOrganisateurMetaGroupe } from 'app/shared/model/organisateur-meta-groupe.model';

@Component({
  selector: 'jhi-organisateur-meta-groupe-detail',
  templateUrl: './organisateur-meta-groupe-detail.component.html'
})
export class OrganisateurMetaGroupeDetailComponent implements OnInit {
  organisateurMetaGroupe: IOrganisateurMetaGroupe | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisateurMetaGroupe }) => (this.organisateurMetaGroupe = organisateurMetaGroupe));
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
