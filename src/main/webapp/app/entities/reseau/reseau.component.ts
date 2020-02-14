import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReseau } from 'app/shared/model/reseau.model';
import { ReseauService } from './reseau.service';
import { ReseauDeleteDialogComponent } from './reseau-delete-dialog.component';

@Component({
  selector: 'jhi-reseau',
  templateUrl: './reseau.component.html'
})
export class ReseauComponent implements OnInit, OnDestroy {
  reseaus?: IReseau[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected reseauService: ReseauService,
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
      this.reseauService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IReseau[]>) => (this.reseaus = res.body || []));
      return;
    }

    this.reseauService.query().subscribe((res: HttpResponse<IReseau[]>) => (this.reseaus = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReseaus();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReseau): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReseaus(): void {
    this.eventSubscriber = this.eventManager.subscribe('reseauListModification', () => this.loadAll());
  }

  delete(reseau: IReseau): void {
    const modalRef = this.modalService.open(ReseauDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reseau = reseau;
  }
}
