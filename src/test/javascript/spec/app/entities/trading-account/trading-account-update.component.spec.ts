/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TrackstockTestModule } from '../../../test.module';
import { TradingAccountUpdateComponent } from 'app/entities/trading-account/trading-account-update.component';
import { TradingAccountService } from 'app/entities/trading-account/trading-account.service';
import { TradingAccount } from 'app/shared/model/trading-account.model';

describe('Component Tests', () => {
  describe('TradingAccount Management Update Component', () => {
    let comp: TradingAccountUpdateComponent;
    let fixture: ComponentFixture<TradingAccountUpdateComponent>;
    let service: TradingAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [TradingAccountUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TradingAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TradingAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TradingAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TradingAccount(123);
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
        const entity = new TradingAccount();
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
