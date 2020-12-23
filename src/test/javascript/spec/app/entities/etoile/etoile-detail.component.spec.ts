import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GalaxyTestModule } from '../../../test.module';
import { EtoileDetailComponent } from 'app/entities/etoile/etoile-detail.component';
import { Etoile } from 'app/shared/model/etoile.model';

describe('Component Tests', () => {
  describe('Etoile Management Detail Component', () => {
    let comp: EtoileDetailComponent;
    let fixture: ComponentFixture<EtoileDetailComponent>;
    const route = ({ data: of({ etoile: new Etoile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GalaxyTestModule],
        declarations: [EtoileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EtoileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtoileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etoile on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etoile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
