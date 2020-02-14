import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMissaUser } from 'app/shared/model/missa-user.model';

@Component({
  selector: 'jhi-missa-user-detail',
  templateUrl: './missa-user-detail.component.html'
})
export class MissaUserDetailComponent implements OnInit {
  missaUser: IMissaUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ missaUser }) => (this.missaUser = missaUser));
  }

  previousState(): void {
    window.history.back();
  }
}
