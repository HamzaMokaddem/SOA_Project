import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GalaxyTestModule } from '../../../test.module';
import { LuneUpdateComponent } from 'app/entities/lune/lune-update.component';
import { LuneService } from 'app/entities/lune/lune.service';
import { Lune } from 'app/shared/model/lune.model';

describe('Component Tests', () => {
  describe('Lune Management Update Component', () => {
    let comp: LuneUpdateComponent;
    let fixture: ComponentFixture<LuneUpdateComponent>;
    let service: LuneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GalaxyTestModule],
        declarations: [LuneUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LuneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LuneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LuneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Lune(123);
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
        const entity = new Lune();
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
