import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtoile } from 'app/shared/model/etoile.model';

@Component({
  selector: 'jhi-etoile-detail',
  templateUrl: './etoile-detail.component.html',
})
export class EtoileDetailComponent implements OnInit {
  etoile: IEtoile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etoile }) => (this.etoile = etoile));
  }

  previousState(): void {
    window.history.back();
  }
}
