import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConnaissance } from 'app/shared/model/connaissance.model';
import { ConnaissanceService } from './connaissance.service';
import { ConnaissanceDeleteDialogComponent } from './connaissance-delete-dialog.component';

@Component({
  selector: 'jhi-connaissance',
  templateUrl: './connaissance.component.html'
})
export class ConnaissanceComponent implements OnInit, OnDestroy {
  connaissances?: IConnaissance[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected connaissanceService: ConnaissanceService,
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
      this.connaissanceService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IConnaissance[]>) => (this.connaissances = res.body || []));
      return;
    }

    this.connaissanceService.query().subscribe((res: HttpResponse<IConnaissance[]>) => (this.connaissances = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConnaissances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConnaissance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConnaissances(): void {
    this.eventSubscriber = this.eventManager.subscribe('connaissanceListModification', () => this.loadAll());
  }

  delete(connaissance: IConnaissance): void {
    const modalRef = this.modalService.open(ConnaissanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.connaissance = connaissance;
  }
}
