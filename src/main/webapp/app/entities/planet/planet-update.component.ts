import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlanet, Planet } from 'app/shared/model/planet.model';
import { PlanetService } from './planet.service';
import { IEtoile } from 'app/shared/model/etoile.model';
import { EtoileService } from 'app/entities/etoile/etoile.service';

@Component({
  selector: 'jhi-planet-update',
  templateUrl: './planet-update.component.html',
})
export class PlanetUpdateComponent implements OnInit {
  isSaving = false;
  etoiles: IEtoile[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(15)]],
    latitude: [null, [Validators.required]],
    longitude: [null, [Validators.required]],
    etat: [null, [Validators.required]],
    etoileId: [null, Validators.required],
  });

  constructor(
    protected planetService: PlanetService,
    protected etoileService: EtoileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planet }) => {
      this.updateForm(planet);

      this.etoileService.query().subscribe((res: HttpResponse<IEtoile[]>) => (this.etoiles = res.body || []));
    });
  }

  updateForm(planet: IPlanet): void {
    this.editForm.patchValue({
      id: planet.id,
      name: planet.name,
      latitude: planet.latitude,
      longitude: planet.longitude,
      etat: planet.etat,
      etoileId: planet.etoileId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planet = this.createFromForm();
    if (planet.id !== undefined) {
      this.subscribeToSaveResponse(this.planetService.update(planet));
    } else {
      this.subscribeToSaveResponse(this.planetService.create(planet));
    }
  }

  private createFromForm(): IPlanet {
    return {
      ...new Planet(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      etoileId: this.editForm.get(['etoileId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanet>>): void {
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

  trackById(index: number, item: IEtoile): any {
    return item.id;
  }
}
