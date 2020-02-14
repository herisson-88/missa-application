import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEnvoiDeMail, EnvoiDeMail } from 'app/shared/model/envoi-de-mail.model';
import { EnvoiDeMailService } from './envoi-de-mail.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MetaGroupeService } from 'app/entities/meta-groupe/meta-groupe.service';
import { IMissaUser } from 'app/shared/model/missa-user.model';
import { MissaUserService } from 'app/entities/missa-user/missa-user.service';

type SelectableEntity = IMetaGroupe | IMissaUser;

@Component({
  selector: 'jhi-envoi-de-mail-update',
  templateUrl: './envoi-de-mail-update.component.html'
})
export class EnvoiDeMailUpdateComponent implements OnInit {
  isSaving = false;
  metagroupes: IMetaGroupe[] = [];
  missausers: IMissaUser[] = [];

  editForm = this.fb.group({
    id: [],
    dateEnvoi: [null, [Validators.required]],
    titre: [null, [Validators.required, Validators.maxLength(200)]],
    adresseMail: [null, [Validators.required]],
    motSpirituel: [],
    conseilTechnique: [],
    nbDestinataire: [null, [Validators.required]],
    sended: [],
    groupePartageParMails: [],
    adminId: [],
    groupeDuMailId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected envoiDeMailService: EnvoiDeMailService,
    protected metaGroupeService: MetaGroupeService,
    protected missaUserService: MissaUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ envoiDeMail }) => {
      if (!envoiDeMail.id) {
        const today = moment().startOf('day');
        envoiDeMail.dateEnvoi = today;
      }

      this.updateForm(envoiDeMail);

      this.metaGroupeService.query().subscribe((res: HttpResponse<IMetaGroupe[]>) => (this.metagroupes = res.body || []));

      this.missaUserService.query().subscribe((res: HttpResponse<IMissaUser[]>) => (this.missausers = res.body || []));
    });
  }

  updateForm(envoiDeMail: IEnvoiDeMail): void {
    this.editForm.patchValue({
      id: envoiDeMail.id,
      dateEnvoi: envoiDeMail.dateEnvoi ? envoiDeMail.dateEnvoi.format(DATE_TIME_FORMAT) : null,
      titre: envoiDeMail.titre,
      adresseMail: envoiDeMail.adresseMail,
      motSpirituel: envoiDeMail.motSpirituel,
      conseilTechnique: envoiDeMail.conseilTechnique,
      nbDestinataire: envoiDeMail.nbDestinataire,
      sended: envoiDeMail.sended,
      groupePartageParMails: envoiDeMail.groupePartageParMails,
      adminId: envoiDeMail.adminId,
      groupeDuMailId: envoiDeMail.groupeDuMailId
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
    const envoiDeMail = this.createFromForm();
    if (envoiDeMail.id !== undefined) {
      this.subscribeToSaveResponse(this.envoiDeMailService.update(envoiDeMail));
    } else {
      this.subscribeToSaveResponse(this.envoiDeMailService.create(envoiDeMail));
    }
  }

  private createFromForm(): IEnvoiDeMail {
    return {
      ...new EnvoiDeMail(),
      id: this.editForm.get(['id'])!.value,
      dateEnvoi: this.editForm.get(['dateEnvoi'])!.value ? moment(this.editForm.get(['dateEnvoi'])!.value, DATE_TIME_FORMAT) : undefined,
      titre: this.editForm.get(['titre'])!.value,
      adresseMail: this.editForm.get(['adresseMail'])!.value,
      motSpirituel: this.editForm.get(['motSpirituel'])!.value,
      conseilTechnique: this.editForm.get(['conseilTechnique'])!.value,
      nbDestinataire: this.editForm.get(['nbDestinataire'])!.value,
      sended: this.editForm.get(['sended'])!.value,
      groupePartageParMails: this.editForm.get(['groupePartageParMails'])!.value,
      adminId: this.editForm.get(['adminId'])!.value,
      groupeDuMailId: this.editForm.get(['groupeDuMailId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnvoiDeMail>>): void {
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

  getSelected(selectedVals: IMetaGroupe[], option: IMetaGroupe): IMetaGroupe {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
