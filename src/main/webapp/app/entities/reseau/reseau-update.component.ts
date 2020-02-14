import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReseau, Reseau } from 'app/shared/model/reseau.model';
import { ReseauService } from './reseau.service';

@Component({
  selector: 'jhi-reseau-update',
  templateUrl: './reseau-update.component.html'
})
export class ReseauUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: []
  });

  constructor(protected reseauService: ReseauService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reseau }) => {
      this.updateForm(reseau);
    });
  }

  updateForm(reseau: IReseau): void {
    this.editForm.patchValue({
      id: reseau.id,
      nom: reseau.nom
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reseau = this.createFromForm();
    if (reseau.id !== undefined) {
      this.subscribeToSaveResponse(this.reseauService.update(reseau));
    } else {
      this.subscribeToSaveResponse(this.reseauService.create(reseau));
    }
  }

  private createFromForm(): IReseau {
    return {
      ...new Reseau(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReseau>>): void {
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
}
