import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GalaxyTestModule } from '../../../test.module';
import { PlanetDetailComponent } from 'app/entities/planet/planet-detail.component';
import { Planet } from 'app/shared/model/planet.model';

describe('Component Tests', () => {
  describe('Planet Management Detail Component', () => {
    let comp: PlanetDetailComponent;
    let fixture: ComponentFixture<PlanetDetailComponent>;
    const route = ({ data: of({ planet: new Planet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GalaxyTestModule],
        declarations: [PlanetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlanetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load planet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
