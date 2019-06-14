/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TrackstockTestModule } from '../../../test.module';
import { NamedPairsDeleteDialogComponent } from 'app/entities/named-pairs/named-pairs-delete-dialog.component';
import { NamedPairsService } from 'app/entities/named-pairs/named-pairs.service';

describe('Component Tests', () => {
  describe('NamedPairs Management Delete Component', () => {
    let comp: NamedPairsDeleteDialogComponent;
    let fixture: ComponentFixture<NamedPairsDeleteDialogComponent>;
    let service: NamedPairsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [NamedPairsDeleteDialogComponent]
      })
        .overrideTemplate(NamedPairsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NamedPairsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NamedPairsService);
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
