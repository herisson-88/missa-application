import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConnaissance } from 'app/shared/model/connaissance.model';

@Component({
  selector: 'jhi-connaissance-detail',
  templateUrl: './connaissance-detail.component.html'
})
export class ConnaissanceDetailComponent implements OnInit {
  connaissance: IConnaissance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ connaissance }) => (this.connaissance = connaissance));
  }

  previousState(): void {
    window.history.back();
  }
}
