/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TrackstockTestModule } from '../../../test.module';
import { WatchlistUpdateComponent } from 'app/entities/watchlist/watchlist-update.component';
import { WatchlistService } from 'app/entities/watchlist/watchlist.service';
import { Watchlist } from 'app/shared/model/watchlist.model';

describe('Component Tests', () => {
  describe('Watchlist Management Update Component', () => {
    let comp: WatchlistUpdateComponent;
    let fixture: ComponentFixture<WatchlistUpdateComponent>;
    let service: WatchlistService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [WatchlistUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WatchlistUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WatchlistUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WatchlistService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Watchlist(123);
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
        const entity = new Watchlist();
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
