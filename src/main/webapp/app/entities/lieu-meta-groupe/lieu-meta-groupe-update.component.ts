import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ILieuMetaGroupe, LieuMetaGroupe } from 'app/shared/model/lieu-meta-groupe.model';
import { LieuMetaGroupeService } from './lieu-meta-groupe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MetaGroupeService } from 'app/entities/meta-groupe/meta-groupe.service';

@Component({
  selector: 'jhi-lieu-meta-groupe-update',
  templateUrl: './lieu-meta-groupe-update.component.html'
})
export class LieuMetaGroupeUpdateComponent implements OnInit {
  isSaving = false;
  metagroupes: IMetaGroupe[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required, Validators.maxLength(100)]],
    adresse: [null, [Validators.maxLength(100)]],
    codePostal: [null, [Validators.maxLength(100)]],
    ville: [null, [Validators.maxLength(100)]],
    lat: [],
    lon: [],
    detail: [],
    departementGroupe: [null, [Validators.maxLength(10)]],
    groupeId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected lieuMetaGroupeService: LieuMetaGroupeService,
    protected metaGroupeService: MetaGroupeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lieuMetaGroupe }) => {
      this.updateForm(lieuMetaGroupe);

      this.metaGroupeService.query().subscribe((res: HttpResponse<IMetaGroupe[]>) => (this.metagroupes = res.body || []));
    });
  }

  updateForm(lieuMetaGroupe: ILieuMetaGroupe): void {
    this.editForm.patchValue({
      id: lieuMetaGroupe.id,
      nom: lieuMetaGroupe.nom,
      adresse: lieuMetaGroupe.adresse,
      codePostal: lieuMetaGroupe.codePostal,
      ville: lieuMetaGroupe.ville,
      lat: lieuMetaGroupe.lat,
      lon: lieuMetaGroupe.lon,
      detail: lieuMetaGroupe.detail,
      departementGroupe: lieuMetaGroupe.departementGroupe,
      groupeId: lieuMetaGroupe.groupeId
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
    const lieuMetaGroupe = this.createFromForm();
    if (lieuMetaGroupe.id !== undefined) {
      this.subscribeToSaveResponse(this.lieuMetaGroupeService.update(lieuMetaGroupe));
    } else {
      this.subscribeToSaveResponse(this.lieuMetaGroupeService.create(lieuMetaGroupe));
    }
  }

  private createFromForm(): ILieuMetaGroupe {
    return {
      ...new LieuMetaGroupe(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      codePostal: this.editForm.get(['codePostal'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      lat: this.editForm.get(['lat'])!.value,
      lon: this.editForm.get(['lon'])!.value,
      detail: this.editForm.get(['detail'])!.value,
      departementGroupe: this.editForm.get(['departementGroupe'])!.value,
      groupeId: this.editForm.get(['groupeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILieuMetaGroupe>>): void {
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

  trackById(index: number, item: IMetaGroupe): any {
    return item.id;
  }
}
