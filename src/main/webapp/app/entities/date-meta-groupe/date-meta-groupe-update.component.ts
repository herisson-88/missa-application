import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDateMetaGroupe, DateMetaGroupe } from 'app/shared/model/date-meta-groupe.model';
import { DateMetaGroupeService } from './date-meta-groupe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MetaGroupeService } from 'app/entities/meta-groupe/meta-groupe.service';

@Component({
  selector: 'jhi-date-meta-groupe-update',
  templateUrl: './date-meta-groupe-update.component.html'
})
export class DateMetaGroupeUpdateComponent implements OnInit {
  isSaving = false;
  metagroupes: IMetaGroupe[] = [];

  editForm = this.fb.group({
    id: [],
    dateDebut: [],
    dateFin: [],
    every: [],
    hour: [],
    minutes: [],
    detail: [],
    groupeId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected dateMetaGroupeService: DateMetaGroupeService,
    protected metaGroupeService: MetaGroupeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dateMetaGroupe }) => {
      if (!dateMetaGroupe.id) {
        const today = moment().startOf('day');
        dateMetaGroupe.dateDebut = today;
        dateMetaGroupe.dateFin = today;
      }

      this.updateForm(dateMetaGroupe);

      this.metaGroupeService.query().subscribe((res: HttpResponse<IMetaGroupe[]>) => (this.metagroupes = res.body || []));
    });
  }

  updateForm(dateMetaGroupe: IDateMetaGroupe): void {
    this.editForm.patchValue({
      id: dateMetaGroupe.id,
      dateDebut: dateMetaGroupe.dateDebut ? dateMetaGroupe.dateDebut.format(DATE_TIME_FORMAT) : null,
      dateFin: dateMetaGroupe.dateFin ? dateMetaGroupe.dateFin.format(DATE_TIME_FORMAT) : null,
      every: dateMetaGroupe.every,
      hour: dateMetaGroupe.hour,
      minutes: dateMetaGroupe.minutes,
      detail: dateMetaGroupe.detail,
      groupeId: dateMetaGroupe.groupeId
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
    const dateMetaGroupe = this.createFromForm();
    if (dateMetaGroupe.id !== undefined) {
      this.subscribeToSaveResponse(this.dateMetaGroupeService.update(dateMetaGroupe));
    } else {
      this.subscribeToSaveResponse(this.dateMetaGroupeService.create(dateMetaGroupe));
    }
  }

  private createFromForm(): IDateMetaGroupe {
    return {
      ...new DateMetaGroupe(),
      id: this.editForm.get(['id'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value ? moment(this.editForm.get(['dateDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      dateFin: this.editForm.get(['dateFin'])!.value ? moment(this.editForm.get(['dateFin'])!.value, DATE_TIME_FORMAT) : undefined,
      every: this.editForm.get(['every'])!.value,
      hour: this.editForm.get(['hour'])!.value,
      minutes: this.editForm.get(['minutes'])!.value,
      detail: this.editForm.get(['detail'])!.value,
      groupeId: this.editForm.get(['groupeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDateMetaGroupe>>): void {
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
