import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConnaissance, Connaissance } from 'app/shared/model/connaissance.model';
import { ConnaissanceService } from './connaissance.service';
import { IMissaUser } from 'app/shared/model/missa-user.model';
import { MissaUserService } from 'app/entities/missa-user/missa-user.service';

@Component({
  selector: 'jhi-connaissance-update',
  templateUrl: './connaissance-update.component.html'
})
export class ConnaissanceUpdateComponent implements OnInit {
  isSaving = false;
  missausers: IMissaUser[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.maxLength(100)]],
    prenom: [null, [Validators.maxLength(100)]],
    mail: [null, [Validators.maxLength(100)]],
    idFaceBook: [null, [Validators.maxLength(200)]],
    parraine: [],
    connuParId: []
  });

  constructor(
    protected connaissanceService: ConnaissanceService,
    protected missaUserService: MissaUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ connaissance }) => {
      this.updateForm(connaissance);

      this.missaUserService.query().subscribe((res: HttpResponse<IMissaUser[]>) => (this.missausers = res.body || []));
    });
  }

  updateForm(connaissance: IConnaissance): void {
    this.editForm.patchValue({
      id: connaissance.id,
      nom: connaissance.nom,
      prenom: connaissance.prenom,
      mail: connaissance.mail,
      idFaceBook: connaissance.idFaceBook,
      parraine: connaissance.parraine,
      connuParId: connaissance.connuParId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const connaissance = this.createFromForm();
    if (connaissance.id !== undefined) {
      this.subscribeToSaveResponse(this.connaissanceService.update(connaissance));
    } else {
      this.subscribeToSaveResponse(this.connaissanceService.create(connaissance));
    }
  }

  private createFromForm(): IConnaissance {
    return {
      ...new Connaissance(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      idFaceBook: this.editForm.get(['idFaceBook'])!.value,
      parraine: this.editForm.get(['parraine'])!.value,
      connuParId: this.editForm.get(['connuParId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConnaissance>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IMissaUser): any {
    return item.id;
  }
}
