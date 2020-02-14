import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMissaUser, MissaUser } from 'app/shared/model/missa-user.model';
import { MissaUserService } from './missa-user.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-missa-user-update',
  templateUrl: './missa-user-update.component.html'
})
export class MissaUserUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    codePostal: [null, [Validators.required, Validators.maxLength(20)]],
    initiales: [null, [Validators.required, Validators.maxLength(20)]],
    nom: [null, [Validators.required, Validators.maxLength(50)]],
    prenom: [null, [Validators.required, Validators.maxLength(50)]],
    mail: [null, [Validators.required, Validators.maxLength(50)]],
    etat: [],
    userId: []
  });

  constructor(
    protected missaUserService: MissaUserService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ missaUser }) => {
      this.updateForm(missaUser);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(missaUser: IMissaUser): void {
    this.editForm.patchValue({
      id: missaUser.id,
      codePostal: missaUser.codePostal,
      initiales: missaUser.initiales,
      nom: missaUser.nom,
      prenom: missaUser.prenom,
      mail: missaUser.mail,
      etat: missaUser.etat,
      userId: missaUser.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const missaUser = this.createFromForm();
    if (missaUser.id !== undefined) {
      this.subscribeToSaveResponse(this.missaUserService.update(missaUser));
    } else {
      this.subscribeToSaveResponse(this.missaUserService.create(missaUser));
    }
  }

  private createFromForm(): IMissaUser {
    return {
      ...new MissaUser(),
      id: this.editForm.get(['id'])!.value,
      codePostal: this.editForm.get(['codePostal'])!.value,
      initiales: this.editForm.get(['initiales'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMissaUser>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
