import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IOrganisateurMetaGroupe, OrganisateurMetaGroupe } from 'app/shared/model/organisateur-meta-groupe.model';
import { OrganisateurMetaGroupeService } from './organisateur-meta-groupe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MetaGroupeService } from 'app/entities/meta-groupe/meta-groupe.service';

@Component({
  selector: 'jhi-organisateur-meta-groupe-update',
  templateUrl: './organisateur-meta-groupe-update.component.html'
})
export class OrganisateurMetaGroupeUpdateComponent implements OnInit {
  isSaving = false;
  metagroupes: IMetaGroupe[] = [];

  editForm = this.fb.group({
    id: [],
    site: [null, [Validators.maxLength(200)]],
    nom: [null, [Validators.maxLength(100)]],
    prenom: [null, [Validators.maxLength(100)]],
    telephone: [null, [Validators.maxLength(20)]],
    mail: [null, [Validators.maxLength(100)]],
    adresse: [null, [Validators.maxLength(100)]],
    codePostal: [null, [Validators.maxLength(20)]],
    ville: [null, [Validators.maxLength(100)]],
    detail: [],
    groupeId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected organisateurMetaGroupeService: OrganisateurMetaGroupeService,
    protected metaGroupeService: MetaGroupeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisateurMetaGroupe }) => {
      this.updateForm(organisateurMetaGroupe);

      this.metaGroupeService.query().subscribe((res: HttpResponse<IMetaGroupe[]>) => (this.metagroupes = res.body || []));
    });
  }

  updateForm(organisateurMetaGroupe: IOrganisateurMetaGroupe): void {
    this.editForm.patchValue({
      id: organisateurMetaGroupe.id,
      site: organisateurMetaGroupe.site,
      nom: organisateurMetaGroupe.nom,
      prenom: organisateurMetaGroupe.prenom,
      telephone: organisateurMetaGroupe.telephone,
      mail: organisateurMetaGroupe.mail,
      adresse: organisateurMetaGroupe.adresse,
      codePostal: organisateurMetaGroupe.codePostal,
      ville: organisateurMetaGroupe.ville,
      detail: organisateurMetaGroupe.detail,
      groupeId: organisateurMetaGroupe.groupeId
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
    const organisateurMetaGroupe = this.createFromForm();
    if (organisateurMetaGroupe.id !== undefined) {
      this.subscribeToSaveResponse(this.organisateurMetaGroupeService.update(organisateurMetaGroupe));
    } else {
      this.subscribeToSaveResponse(this.organisateurMetaGroupeService.create(organisateurMetaGroupe));
    }
  }

  private createFromForm(): IOrganisateurMetaGroupe {
    return {
      ...new OrganisateurMetaGroupe(),
      id: this.editForm.get(['id'])!.value,
      site: this.editForm.get(['site'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      codePostal: this.editForm.get(['codePostal'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      detail: this.editForm.get(['detail'])!.value,
      groupeId: this.editForm.get(['groupeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganisateurMetaGroupe>>): void {
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
