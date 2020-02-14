import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILieuMetaGroupe } from 'app/shared/model/lieu-meta-groupe.model';
import { LieuMetaGroupeService } from './lieu-meta-groupe.service';
import { LieuMetaGroupeDeleteDialogComponent } from './lieu-meta-groupe-delete-dialog.component';

@Component({
  selector: 'jhi-lieu-meta-groupe',
  templateUrl: './lieu-meta-groupe.component.html'
})
export class LieuMetaGroupeComponent implements OnInit, OnDestroy {
  lieuMetaGroupes?: ILieuMetaGroupe[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected lieuMetaGroupeService: LieuMetaGroupeService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.lieuMetaGroupeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ILieuMetaGroupe[]>) => (this.lieuMetaGroupes = res.body || []));
      return;
    }

    this.lieuMetaGroupeService.query().subscribe((res: HttpResponse<ILieuMetaGroupe[]>) => (this.lieuMetaGroupes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLieuMetaGroupes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILieuMetaGroupe): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInLieuMetaGroupes(): void {
    this.eventSubscriber = this.eventManager.subscribe('lieuMetaGroupeListModification', () => this.loadAll());
  }

  delete(lieuMetaGroupe: ILieuMetaGroupe): void {
    const modalRef = this.modalService.open(LieuMetaGroupeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lieuMetaGroupe = lieuMetaGroupe;
  }
}
