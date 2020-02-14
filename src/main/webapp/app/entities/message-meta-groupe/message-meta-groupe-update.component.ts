import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IMessageMetaGroupe, MessageMetaGroupe } from 'app/shared/model/message-meta-groupe.model';
import { MessageMetaGroupeService } from './message-meta-groupe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IMissaUser } from 'app/shared/model/missa-user.model';
import { MissaUserService } from 'app/entities/missa-user/missa-user.service';
import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MetaGroupeService } from 'app/entities/meta-groupe/meta-groupe.service';

type SelectableEntity = IMissaUser | IMetaGroupe;

@Component({
  selector: 'jhi-message-meta-groupe-update',
  templateUrl: './message-meta-groupe-update.component.html'
})
export class MessageMetaGroupeUpdateComponent implements OnInit {
  isSaving = false;
  missausers: IMissaUser[] = [];
  metagroupes: IMetaGroupe[] = [];

  editForm = this.fb.group({
    id: [],
    titre: [null, [Validators.maxLength(100)]],
    publique: [],
    message: [null, [Validators.required]],
    auteurId: [],
    groupeId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected messageMetaGroupeService: MessageMetaGroupeService,
    protected missaUserService: MissaUserService,
    protected metaGroupeService: MetaGroupeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ messageMetaGroupe }) => {
      this.updateForm(messageMetaGroupe);

      this.missaUserService.query().subscribe((res: HttpResponse<IMissaUser[]>) => (this.missausers = res.body || []));

      this.metaGroupeService.query().subscribe((res: HttpResponse<IMetaGroupe[]>) => (this.metagroupes = res.body || []));
    });
  }

  updateForm(messageMetaGroupe: IMessageMetaGroupe): void {
    this.editForm.patchValue({
      id: messageMetaGroupe.id,
      titre: messageMetaGroupe.titre,
      publique: messageMetaGroupe.publique,
      message: messageMetaGroupe.message,
      auteurId: messageMetaGroupe.auteurId,
      groupeId: messageMetaGroupe.groupeId
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
    const messageMetaGroupe = this.createFromForm();
    if (messageMetaGroupe.id !== undefined) {
      this.subscribeToSaveResponse(this.messageMetaGroupeService.update(messageMetaGroupe));
    } else {
      this.subscribeToSaveResponse(this.messageMetaGroupeService.create(messageMetaGroupe));
    }
  }

  private createFromForm(): IMessageMetaGroupe {
    return {
      ...new MessageMetaGroupe(),
      id: this.editForm.get(['id'])!.value,
      titre: this.editForm.get(['titre'])!.value,
      publique: this.editForm.get(['publique'])!.value,
      message: this.editForm.get(['message'])!.value,
      auteurId: this.editForm.get(['auteurId'])!.value,
      groupeId: this.editForm.get(['groupeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessageMetaGroupe>>): void {
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
