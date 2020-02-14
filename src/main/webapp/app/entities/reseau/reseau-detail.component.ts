import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReseau } from 'app/shared/model/reseau.model';

@Component({
  selector: 'jhi-reseau-detail',
  templateUrl: './reseau-detail.component.html'
})
export class ReseauDetailComponent implements OnInit {
  reseau: IReseau | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reseau }) => (this.reseau = reseau));
  }

  previousState(): void {
    window.history.back();
  }
}
