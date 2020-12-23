import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtoile, Etoile } from 'app/shared/model/etoile.model';
import { EtoileService } from './etoile.service';

@Component({
  selector: 'jhi-etoile-update',
  templateUrl: './etoile-update.component.html',
})
export class EtoileUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(15)]],
    latitude: [null, [Validators.required]],
    longitude: [null, [Validators.required]],
  });

  constructor(protected etoileService: EtoileService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etoile }) => {
      this.updateForm(etoile);
    });
  }

  updateForm(etoile: IEtoile): void {
    this.editForm.patchValue({
      id: etoile.id,
      name: etoile.name,
      latitude: etoile.latitude,
      longitude: etoile.longitude,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etoile = this.createFromForm();
    if (etoile.id !== undefined) {
      this.subscribeToSaveResponse(this.etoileService.update(etoile));
    } else {
      this.subscribeToSaveResponse(this.etoileService.create(etoile));
    }
  }

  private createFromForm(): IEtoile {
    return {
      ...new Etoile(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtoile>>): void {
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
}
