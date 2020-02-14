import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ILienDocMetaGroupe, LienDocMetaGroupe } from 'app/shared/model/lien-doc-meta-groupe.model';
import { LienDocMetaGroupeService } from './lien-doc-meta-groupe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IMetaGroupe } from 'app/shared/model/meta-groupe.model';
import { MetaGroupeService } from 'app/entities/meta-groupe/meta-groupe.service';
import { ITypeMetaGroupe } from 'app/shared/model/type-meta-groupe.model';
import { TypeMetaGroupeService } from 'app/entities/type-meta-groupe/type-meta-groupe.service';

type SelectableEntity = IMetaGroupe | ITypeMetaGroupe;

@Component({
  selector: 'jhi-lien-doc-meta-groupe-update',
  templateUrl: './lien-doc-meta-groupe-update.component.html'
})
export class LienDocMetaGroupeUpdateComponent implements OnInit {
  isSaving = false;
  metagroupes: IMetaGroupe[] = [];
  typemetagroupes: ITypeMetaGroupe[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required, Validators.maxLength(100)]],
    typeDuDoc: [null, [Validators.required]],
    icone: [null, []],
    iconeContentType: [],
    image: [null, []],
    imageContentType: [],
    doc: [null, []],
    docContentType: [],
    detail: [],
    groupeId: [],
    typeId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected lienDocMetaGroupeService: LienDocMetaGroupeService,
    protected metaGroupeService: MetaGroupeService,
    protected typeMetaGroupeService: TypeMetaGroupeService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lienDocMetaGroupe }) => {
      this.updateForm(lienDocMetaGroupe);

      this.metaGroupeService.query().subscribe((res: HttpResponse<IMetaGroupe[]>) => (this.metagroupes = res.body || []));

      this.typeMetaGroupeService.query().subscribe((res: HttpResponse<ITypeMetaGroupe[]>) => (this.typemetagroupes = res.body || []));
    });
  }

  updateForm(lienDocMetaGroupe: ILienDocMetaGroupe): void {
    this.editForm.patchValue({
      id: lienDocMetaGroupe.id,
      nom: lienDocMetaGroupe.nom,
      typeDuDoc: lienDocMetaGroupe.typeDuDoc,
      icone: lienDocMetaGroupe.icone,
      iconeContentType: lienDocMetaGroupe.iconeContentType,
      image: lienDocMetaGroupe.image,
      imageContentType: lienDocMetaGroupe.imageContentType,
      doc: lienDocMetaGroupe.doc,
      docContentType: lienDocMetaGroupe.docContentType,
      detail: lienDocMetaGroupe.detail,
      groupeId: lienDocMetaGroupe.groupeId,
      typeId: lienDocMetaGroupe.typeId
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lienDocMetaGroupe = this.createFromForm();
    if (lienDocMetaGroupe.id !== undefined) {
      this.subscribeToSaveResponse(this.lienDocMetaGroupeService.update(lienDocMetaGroupe));
    } else {
      this.subscribeToSaveResponse(this.lienDocMetaGroupeService.create(lienDocMetaGroupe));
    }
  }

  private createFromForm(): ILienDocMetaGroupe {
    return {
      ...new LienDocMetaGroupe(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      typeDuDoc: this.editForm.get(['typeDuDoc'])!.value,
      iconeContentType: this.editForm.get(['iconeContentType'])!.value,
      icone: this.editForm.get(['icone'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      docContentType: this.editForm.get(['docContentType'])!.value,
      doc: this.editForm.get(['doc'])!.value,
      detail: this.editForm.get(['detail'])!.value,
      groupeId: this.editForm.get(['groupeId'])!.value,
      typeId: this.editForm.get(['typeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILienDocMetaGroupe>>): void {
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
