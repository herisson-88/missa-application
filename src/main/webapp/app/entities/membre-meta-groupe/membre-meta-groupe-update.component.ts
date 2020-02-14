import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IMembreMetaGroupe, MembreMetaGroupe } from 'app/shared/model/membre-meta-groupe.model';
import { MembreMetaGroupeService } from './membre-meta-groupe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MetaGroupeService } from 'app/entities/meta-groupe/meta-groupe.service';
import { IMissaUser } from 'app/shared/model/missa-user.model';
import { MissaUserService } from 'app/entities/missa-user/missa-user.service';

type SelectableEntity = IMetaGroupe | IMissaUser;

@Component({
  selector: 'jhi-membre-meta-groupe-update',
  templateUrl: './membre-meta-groupe-update.component.html'
})
export class MembreMetaGroupeUpdateComponent implements OnInit {
  isSaving = false;
  metagroupes: IMetaGroupe[] = [];
  missausers: IMissaUser[] = [];

  editForm = this.fb.group({
    id: [],
    validated: [],
    dateValidation: [],
    admin: [],
    presentation: [null, [Validators.required]],
    connaissance: [null, [Validators.required]],
    mailingList: [null, [Validators.required]],
    groupeMembreId: [],
    valideParId: [],
    missaUserId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected membreMetaGroupeService: MembreMetaGroupeService,
    protected metaGroupeService: MetaGroupeService,
    protected missaUserService: MissaUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membreMetaGroupe }) => {
      if (!membreMetaGroupe.id) {
        const today = moment().startOf('day');
        membreMetaGroupe.dateValidation = today;
      }

      this.updateForm(membreMetaGroupe);

      this.metaGroupeService.query().subscribe((res: HttpResponse<IMetaGroupe[]>) => (this.metagroupes = res.body || []));

      this.missaUserService.query().subscribe((res: HttpResponse<IMissaUser[]>) => (this.missausers = res.body || []));
    });
  }

  updateForm(membreMetaGroupe: IMembreMetaGroupe): void {
    this.editForm.patchValue({
      id: membreMetaGroupe.id,
      validated: membreMetaGroupe.validated,
      dateValidation: membreMetaGroupe.dateValidation ? membreMetaGroupe.dateValidation.format(DATE_TIME_FORMAT) : null,
      admin: membreMetaGroupe.admin,
      presentation: membreMetaGroupe.presentation,
      connaissance: membreMetaGroupe.connaissance,
      mailingList: membreMetaGroupe.mailingList,
      groupeMembreId: membreMetaGroupe.groupeMembreId,
      valideParId: membreMetaGroupe.valideParId,
      missaUserId: membreMetaGroupe.missaUserId
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
    const membreMetaGroupe = this.createFromForm();
    if (membreMetaGroupe.id !== undefined) {
      this.subscribeToSaveResponse(this.membreMetaGroupeService.update(membreMetaGroupe));
    } else {
      this.subscribeToSaveResponse(this.membreMetaGroupeService.create(membreMetaGroupe));
    }
  }

  private createFromForm(): IMembreMetaGroupe {
    return {
      ...new MembreMetaGroupe(),
      id: this.editForm.get(['id'])!.value,
      validated: this.editForm.get(['validated'])!.value,
      dateValidation: this.editForm.get(['dateValidation'])!.value
        ? moment(this.editForm.get(['dateValidation'])!.value, DATE_TIME_FORMAT)
        : undefined,
      admin: this.editForm.get(['admin'])!.value,
      presentation: this.editForm.get(['presentation'])!.value,
      connaissance: this.editForm.get(['connaissance'])!.value,
      mailingList: this.editForm.get(['mailingList'])!.value,
      groupeMembreId: this.editForm.get(['groupeMembreId'])!.value,
      valideParId: this.editForm.get(['valideParId'])!.value,
      missaUserId: this.editForm.get(['missaUserId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembreMetaGroupe>>): void {
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
