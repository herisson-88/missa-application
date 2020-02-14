import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IMetaGroupe, MetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MetaGroupeService } from './meta-groupe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';
import { TypeMetaGroupeService } from 'app/entities/type-meta-groupe/type-meta-groupe.service';
import { IReseau } from 'app/shared/model/reseau.model';
import { ReseauService } from 'app/entities/reseau/reseau.service';

type SelectableEntity = IMetaGroupe | ITypeMetaGroupe | IReseau;

@Component({
  selector: 'jhi-meta-groupe-update',
  templateUrl: './meta-groupe-update.component.html'
})
export class MetaGroupeUpdateComponent implements OnInit {
  isSaving = false;
  metagroupes: IMetaGroupe[] = [];
  typemetagroupes: ITypeMetaGroupe[] = [];
  reseaus: IReseau[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required, Validators.maxLength(100)]],
    autoriteValidation: [],
    membreParent: [],
    visibilite: [],
    droitEnvoiDeMail: [],
    demandeDiffusionVerticale: [],
    messagerieModeree: [],
    groupeFinal: [],
    dateValidation: [],
    detail: [],
    parentId: [],
    typeId: [],
    reseauId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected metaGroupeService: MetaGroupeService,
    protected typeMetaGroupeService: TypeMetaGroupeService,
    protected reseauService: ReseauService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ metaGroupe }) => {
      if (!metaGroupe.id) {
        const today = moment().startOf('day');
        metaGroupe.dateValidation = today;
      }

      this.updateForm(metaGroupe);

      this.metaGroupeService.query().subscribe((res: HttpResponse<IMetaGroupe[]>) => (this.metagroupes = res.body || []));

      this.typeMetaGroupeService.query().subscribe((res: HttpResponse<ITypeMetaGroupe[]>) => (this.typemetagroupes = res.body || []));

      this.reseauService.query().subscribe((res: HttpResponse<IReseau[]>) => (this.reseaus = res.body || []));
    });
  }

  updateForm(metaGroupe: IMetaGroupe): void {
    this.editForm.patchValue({
      id: metaGroupe.id,
      nom: metaGroupe.nom,
      autoriteValidation: metaGroupe.autoriteValidation,
      membreParent: metaGroupe.membreParent,
      visibilite: metaGroupe.visibilite,
      droitEnvoiDeMail: metaGroupe.droitEnvoiDeMail,
      demandeDiffusionVerticale: metaGroupe.demandeDiffusionVerticale,
      messagerieModeree: metaGroupe.messagerieModeree,
      groupeFinal: metaGroupe.groupeFinal,
      dateValidation: metaGroupe.dateValidation ? metaGroupe.dateValidation.format(DATE_TIME_FORMAT) : null,
      detail: metaGroupe.detail,
      parentId: metaGroupe.parentId,
      typeId: metaGroupe.typeId,
      reseauId: metaGroupe.reseauId
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
    const metaGroupe = this.createFromForm();
    if (metaGroupe.id !== undefined) {
      this.subscribeToSaveResponse(this.metaGroupeService.update(metaGroupe));
    } else {
      this.subscribeToSaveResponse(this.metaGroupeService.create(metaGroupe));
    }
  }

  private createFromForm(): IMetaGroupe {
    return {
      ...new MetaGroupe(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      autoriteValidation: this.editForm.get(['autoriteValidation'])!.value,
      membreParent: this.editForm.get(['membreParent'])!.value,
      visibilite: this.editForm.get(['visibilite'])!.value,
      droitEnvoiDeMail: this.editForm.get(['droitEnvoiDeMail'])!.value,
      demandeDiffusionVerticale: this.editForm.get(['demandeDiffusionVerticale'])!.value,
      messagerieModeree: this.editForm.get(['messagerieModeree'])!.value,
      groupeFinal: this.editForm.get(['groupeFinal'])!.value,
      dateValidation: this.editForm.get(['dateValidation'])!.value
        ? moment(this.editForm.get(['dateValidation'])!.value, DATE_TIME_FORMAT)
        : undefined,
      detail: this.editForm.get(['detail'])!.value,
      parentId: this.editForm.get(['parentId'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
      reseauId: this.editForm.get(['reseauId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMetaGroupe>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
