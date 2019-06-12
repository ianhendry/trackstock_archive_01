/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrackstockTestModule } from '../../../test.module';
import { TradingAccountComponent } from 'app/entities/trading-account/trading-account.component';
import { TradingAccountService } from 'app/entities/trading-account/trading-account.service';
import { TradingAccount } from 'app/shared/model/trading-account.model';

describe('Component Tests', () => {
  describe('TradingAccount Management Component', () => {
    let comp: TradingAccountComponent;
    let fixture: ComponentFixture<TradingAccountComponent>;
    let service: TradingAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [TradingAccountComponent],
        providers: []
      })
        .overrideTemplate(TradingAccountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TradingAccountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TradingAccountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TradingAccount(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tradingAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
