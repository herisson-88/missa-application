import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMessageMetaGroupe } from 'app/shared/model/message-meta-groupe.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MessageMetaGroupeService } from './message-meta-groupe.service';
import { MessageMetaGroupeDeleteDialogComponent } from './message-meta-groupe-delete-dialog.component';

@Component({
  selector: 'jhi-message-meta-groupe',
  templateUrl: './message-meta-groupe.component.html'
})
export class MessageMetaGroupeComponent implements OnInit, OnDestroy {
  messageMetaGroupes: IMessageMetaGroupe[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  currentSearch: string;

  constructor(
    protected messageMetaGroupeService: MessageMetaGroupeService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute
  ) {
    this.messageMetaGroupes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.messageMetaGroupeService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IMessageMetaGroupe[]>) => this.paginateMessageMetaGroupes(res.body, res.headers));
      return;
    }

    this.messageMetaGroupeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMessageMetaGroupe[]>) => this.paginateMessageMetaGroupes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.messageMetaGroupes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  search(query: string): void {
    this.messageMetaGroupes = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    if (query) {
      this.predicate = '_score';
      this.ascending = false;
    } else {
      this.predicate = 'id';
      this.ascending = true;
    }
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMessageMetaGroupes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMessageMetaGroupe): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInMessageMetaGroupes(): void {
    this.eventSubscriber = this.eventManager.subscribe('messageMetaGroupeListModification', () => this.reset());
  }

  delete(messageMetaGroupe: IMessageMetaGroupe): void {
    const modalRef = this.modalService.open(MessageMetaGroupeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.messageMetaGroupe = messageMetaGroupe;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMessageMetaGroupes(data: IMessageMetaGroupe[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.messageMetaGroupes.push(data[i]);
      }
    }
  }
}
