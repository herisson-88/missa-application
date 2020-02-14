import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPartageMetaGroupe, PartageMetaGroupe } from 'app/shared/model/partage-meta-groupe.model';
import { PartageMetaGroupeService } from './partage-meta-groupe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MetaGroupeService } from 'app/entities/meta-groupe/meta-groupe.service';
import { IMissaUser } from 'app/shared/model/missa-user.model';
import { MissaUserService } from 'app/entities/missa-user/missa-user.service';

type SelectableEntity = IMetaGroupe | IMissaUser;

@Component({
  selector: 'jhi-partage-meta-groupe-update',
  templateUrl: './partage-meta-groupe-update.component.html'
})
export class PartageMetaGroupeUpdateComponent implements OnInit {
  isSaving = false;
  metagroupes: IMetaGroupe[] = [];
  missausers: IMissaUser[] = [];

  editForm = this.fb.group({
    id: [],
    validated: [],
    dateValidation: [],
    detail: [],
    metaGroupePartageId: [],
    metaGroupeDestinatairesId: [],
    auteurPartageId: [],
    validateurDuPartageId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected partageMetaGroupeService: PartageMetaGroupeService,
    protected metaGroupeService: MetaGroupeService,
    protected missaUserService: MissaUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partageMetaGroupe }) => {
      if (!partageMetaGroupe.id) {
        const today = moment().startOf('day');
        partageMetaGroupe.dateValidation = today;
      }

      this.updateForm(partageMetaGroupe);

      this.metaGroupeService.query().subscribe((res: HttpResponse<IMetaGroupe[]>) => (this.metagroupes = res.body || []));

      this.missaUserService.query().subscribe((res: HttpResponse<IMissaUser[]>) => (this.missausers = res.body || []));
    });
  }

  updateForm(partageMetaGroupe: IPartageMetaGroupe): void {
    this.editForm.patchValue({
      id: partageMetaGroupe.id,
      validated: partageMetaGroupe.validated,
      dateValidation: partageMetaGroupe.dateValidation ? partageMetaGroupe.dateValidation.format(DATE_TIME_FORMAT) : null,
      detail: partageMetaGroupe.detail,
      metaGroupePartageId: partageMetaGroupe.metaGroupePartageId,
      metaGroupeDestinatairesId: partageMetaGroupe.metaGroupeDestinatairesId,
      auteurPartageId: partageMetaGroupe.auteurPartageId,
      validateurDuPartageId: partageMetaGroupe.validateurDuPartageId
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
    const partageMetaGroupe = this.createFromForm();
    if (partageMetaGroupe.id !== undefined) {
      this.subscribeToSaveResponse(this.partageMetaGroupeService.update(partageMetaGroupe));
    } else {
      this.subscribeToSaveResponse(this.partageMetaGroupeService.create(partageMetaGroupe));
    }
  }

  private createFromForm(): IPartageMetaGroupe {
    return {
      ...new PartageMetaGroupe(),
      id: this.editForm.get(['id'])!.value,
      validated: this.editForm.get(['validated'])!.value,
      dateValidation: this.editForm.get(['dateValidation'])!.value
        ? moment(this.editForm.get(['dateValidation'])!.value, DATE_TIME_FORMAT)
        : undefined,
      detail: this.editForm.get(['detail'])!.value,
      metaGroupePartageId: this.editForm.get(['metaGroupePartageId'])!.value,
      metaGroupeDestinatairesId: this.editForm.get(['metaGroupeDestinatairesId'])!.value,
      auteurPartageId: this.editForm.get(['auteurPartageId'])!.value,
      validateurDuPartageId: this.editForm.get(['validateurDuPartageId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartageMetaGroupe>>): void {
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
