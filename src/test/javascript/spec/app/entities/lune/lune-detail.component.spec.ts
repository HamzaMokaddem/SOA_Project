import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GalaxyTestModule } from '../../../test.module';
import { LuneDetailComponent } from 'app/entities/lune/lune-detail.component';
import { Lune } from 'app/shared/model/lune.model';

describe('Component Tests', () => {
  describe('Lune Management Detail Component', () => {
    let comp: LuneDetailComponent;
    let fixture: ComponentFixture<LuneDetailComponent>;
    const route = ({ data: of({ lune: new Lune(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GalaxyTestModule],
        declarations: [LuneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LuneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LuneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lune on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lune).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
