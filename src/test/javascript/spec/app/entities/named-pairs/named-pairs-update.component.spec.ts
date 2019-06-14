/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TrackstockTestModule } from '../../../test.module';
import { NamedPairsUpdateComponent } from 'app/entities/named-pairs/named-pairs-update.component';
import { NamedPairsService } from 'app/entities/named-pairs/named-pairs.service';
import { NamedPairs } from 'app/shared/model/named-pairs.model';

describe('Component Tests', () => {
  describe('NamedPairs Management Update Component', () => {
    let comp: NamedPairsUpdateComponent;
    let fixture: ComponentFixture<NamedPairsUpdateComponent>;
    let service: NamedPairsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [NamedPairsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NamedPairsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NamedPairsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NamedPairsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NamedPairs(123);
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
        const entity = new NamedPairs();
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
