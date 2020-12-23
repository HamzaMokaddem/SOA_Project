import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILune } from 'app/shared/model/lune.model';

@Component({
  selector: 'jhi-lune-detail',
  templateUrl: './lune-detail.component.html',
})
export class LuneDetailComponent implements OnInit {
  lune: ILune | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lune }) => (this.lune = lune));
  }

  previousState(): void {
    window.history.back();
  }
}
