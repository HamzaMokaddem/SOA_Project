import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GalaxyTestModule } from '../../../test.module';
import { EtoileUpdateComponent } from 'app/entities/etoile/etoile-update.component';
import { EtoileService } from 'app/entities/etoile/etoile.service';
import { Etoile } from 'app/shared/model/etoile.model';

describe('Component Tests', () => {
  describe('Etoile Management Update Component', () => {
    let comp: EtoileUpdateComponent;
    let fixture: ComponentFixture<EtoileUpdateComponent>;
    let service: EtoileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GalaxyTestModule],
        declarations: [EtoileUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EtoileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtoileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtoileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Etoile(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Etoile();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
