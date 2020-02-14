import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDateMetaGroupe } from 'app/shared/model/date-meta-groupe.model';
import { DateMetaGroupeService } from './date-meta-groupe.service';
import { DateMetaGroupeDeleteDialogComponent } from './date-meta-groupe-delete-dialog.component';

@Component({
  selector: 'jhi-date-meta-groupe',
  templateUrl: './date-meta-groupe.component.html'
})
export class DateMetaGroupeComponent implements OnInit, OnDestroy {
  dateMetaGroupes?: IDateMetaGroupe[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected dateMetaGroupeService: DateMetaGroupeService,
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
      this.dateMetaGroupeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IDateMetaGroupe[]>) => (this.dateMetaGroupes = res.body || []));
      return;
    }

    this.dateMetaGroupeService.query().subscribe((res: HttpResponse<IDateMetaGroupe[]>) => (this.dateMetaGroupes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDateMetaGroupes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDateMetaGroupe): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInDateMetaGroupes(): void {
    this.eventSubscriber = this.eventManager.subscribe('dateMetaGroupeListModification', () => this.loadAll());
  }

  delete(dateMetaGroupe: IDateMetaGroupe): void {
    const modalRef = this.modalService.open(DateMetaGroupeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dateMetaGroupe = dateMetaGroupe;
  }
}
