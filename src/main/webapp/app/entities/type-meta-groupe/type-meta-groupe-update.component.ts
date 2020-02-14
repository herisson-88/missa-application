import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITypeMetaGroupe, TypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';
import { TypeMetaGroupeService } from './type-meta-groupe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IReseau } from 'app/shared/model/reseau.model';
import { ReseauService } from 'app/entities/reseau/reseau.service';

@Component({
  selector: 'jhi-type-meta-groupe-update',
  templateUrl: './type-meta-groupe-update.component.html'
})
export class TypeMetaGroupeUpdateComponent implements OnInit {
  isSaving = false;
  reseaus: IReseau[] = [];

  editForm = this.fb.group({
    id: [],
    typeDuGroupe: [null, [Validators.required, Validators.maxLength(100)]],
    iconeFa: [null, [Validators.maxLength(200)]],
    detail: [],
    ordreMail: [],
    reseauId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected typeMetaGroupeService: TypeMetaGroupeService,
    protected reseauService: ReseauService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeMetaGroupe }) => {
      this.updateForm(typeMetaGroupe);

      this.reseauService.query().subscribe((res: HttpResponse<IReseau[]>) => (this.reseaus = res.body || []));
    });
  }

  updateForm(typeMetaGroupe: ITypeMetaGroupe): void {
    this.editForm.patchValue({
      id: typeMetaGroupe.id,
      typeDuGroupe: typeMetaGroupe.typeDuGroupe,
      iconeFa: typeMetaGroupe.iconeFa,
      detail: typeMetaGroupe.detail,
      ordreMail: typeMetaGroupe.ordreMail,
      reseauId: typeMetaGroupe.reseauId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('missaApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeMetaGroupe = this.createFromForm();
    if (typeMetaGroupe.id !== undefined) {
      this.subscribeToSaveResponse(this.typeMetaGroupeService.update(typeMetaGroupe));
    } else {
      this.subscribeToSaveResponse(this.typeMetaGroupeService.create(typeMetaGroupe));
    }
  }

  private createFromForm(): ITypeMetaGroupe {
    return {
      ...new TypeMetaGroupe(),
      id: this.editForm.get(['id'])!.value,
      typeDuGroupe: this.editForm.get(['typeDuGroupe'])!.value,
      iconeFa: this.editForm.get(['iconeFa'])!.value,
      detail: this.editForm.get(['detail'])!.value,
      ordreMail: this.editForm.get(['ordreMail'])!.value,
      reseauId: this.editForm.get(['reseauId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeMetaGroupe>>): void {
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

  trackById(index: number, item: IReseau): any {
    return item.id;
  }
}
