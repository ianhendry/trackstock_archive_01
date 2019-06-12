/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TrackstockTestModule } from '../../../test.module';
import { TradingAccountDeleteDialogComponent } from 'app/entities/trading-account/trading-account-delete-dialog.component';
import { TradingAccountService } from 'app/entities/trading-account/trading-account.service';

describe('Component Tests', () => {
  describe('TradingAccount Management Delete Component', () => {
    let comp: TradingAccountDeleteDialogComponent;
    let fixture: ComponentFixture<TradingAccountDeleteDialogComponent>;
    let service: TradingAccountService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [TradingAccountDeleteDialogComponent]
      })
        .overrideTemplate(TradingAccountDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TradingAccountDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TradingAccountService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
