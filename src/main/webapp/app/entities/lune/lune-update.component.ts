import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILune, Lune } from 'app/shared/model/lune.model';
import { LuneService } from './lune.service';
import { IPlanet } from 'app/shared/model/planet.model';
import { PlanetService } from 'app/entities/planet/planet.service';

@Component({
  selector: 'jhi-lune-update',
  templateUrl: './lune-update.component.html',
})
export class LuneUpdateComponent implements OnInit {
  isSaving = false;
  planets: IPlanet[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(15)]],
    latitude: [null, [Validators.required]],
    longitude: [null, [Validators.required]],
    taille: [null, [Validators.required]],
    planetId: [null, Validators.required],
  });

  constructor(
    protected luneService: LuneService,
    protected planetService: PlanetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lune }) => {
      this.updateForm(lune);

      this.planetService.query().subscribe((res: HttpResponse<IPlanet[]>) => (this.planets = res.body || []));
    });
  }

  updateForm(lune: ILune): void {
    this.editForm.patchValue({
      id: lune.id,
      name: lune.name,
      latitude: lune.latitude,
      longitude: lune.longitude,
      taille: lune.taille,
      planetId: lune.planetId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lune = this.createFromForm();
    if (lune.id !== undefined) {
      this.subscribeToSaveResponse(this.luneService.update(lune));
    } else {
      this.subscribeToSaveResponse(this.luneService.create(lune));
    }
  }

  private createFromForm(): ILune {
    return {
      ...new Lune(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      taille: this.editForm.get(['taille'])!.value,
      planetId: this.editForm.get(['planetId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILune>>): void {
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

  trackById(index: number, item: IPlanet): any {
    return item.id;
  }
}
