import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganisateurMetaGroupe } from 'app/shared/model/organisateur-meta-groupe.model';
import { OrganisateurMetaGroupeService } from './organisateur-meta-groupe.service';
import { OrganisateurMetaGroupeDeleteDialogComponent } from './organisateur-meta-groupe-delete-dialog.component';

@Component({
  selector: 'jhi-organisateur-meta-groupe',
  templateUrl: './organisateur-meta-groupe.component.html'
})
export class OrganisateurMetaGroupeComponent implements OnInit, OnDestroy {
  organisateurMetaGroupes?: IOrganisateurMetaGroupe[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected organisateurMetaGroupeService: OrganisateurMetaGroupeService,
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
      this.organisateurMetaGroupeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IOrganisateurMetaGroupe[]>) => (this.organisateurMetaGroupes = res.body || []));
      return;
    }

    this.organisateurMetaGroupeService
      .query()
      .subscribe((res: HttpResponse<IOrganisateurMetaGroupe[]>) => (this.organisateurMetaGroupes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrganisateurMetaGroupes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrganisateurMetaGroupe): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInOrganisateurMetaGroupes(): void {
    this.eventSubscriber = this.eventManager.subscribe('organisateurMetaGroupeListModification', () => this.loadAll());
  }

  delete(organisateurMetaGroupe: IOrganisateurMetaGroupe): void {
    const modalRef = this.modalService.open(OrganisateurMetaGroupeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.organisateurMetaGroupe = organisateurMetaGroupe;
  }
}
