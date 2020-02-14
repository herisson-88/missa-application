import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';
import { TypeMetaGroupeService } from './type-meta-groupe.service';
import { TypeMetaGroupeDeleteDialogComponent } from './type-meta-groupe-delete-dialog.component';

@Component({
  selector: 'jhi-type-meta-groupe',
  templateUrl: './type-meta-groupe.component.html'
})
export class TypeMetaGroupeComponent implements OnInit, OnDestroy {
  typeMetaGroupes?: ITypeMetaGroupe[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected typeMetaGroupeService: TypeMetaGroupeService,
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
      this.typeMetaGroupeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITypeMetaGroupe[]>) => (this.typeMetaGroupes = res.body || []));
      return;
    }

    this.typeMetaGroupeService.query().subscribe((res: HttpResponse<ITypeMetaGroupe[]>) => (this.typeMetaGroupes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeMetaGroupes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeMetaGroupe): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInTypeMetaGroupes(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeMetaGroupeListModification', () => this.loadAll());
  }

  delete(typeMetaGroupe: ITypeMetaGroupe): void {
    const modalRef = this.modalService.open(TypeMetaGroupeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeMetaGroupe = typeMetaGroupe;
  }
}
