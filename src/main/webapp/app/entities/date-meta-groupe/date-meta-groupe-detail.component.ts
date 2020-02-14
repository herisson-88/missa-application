import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDateMetaGroupe } from 'app/shared/model/date-meta-groupe.model';

@Component({
  selector: 'jhi-date-meta-groupe-detail',
  templateUrl: './date-meta-groupe-detail.component.html'
})
export class DateMetaGroupeDetailComponent implements OnInit {
  dateMetaGroupe: IDateMetaGroupe | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dateMetaGroupe }) => (this.dateMetaGroupe = dateMetaGroupe));
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
