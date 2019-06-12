/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrackstockTestModule } from '../../../test.module';
import { TradingAccountDetailComponent } from 'app/entities/trading-account/trading-account-detail.component';
import { TradingAccount } from 'app/shared/model/trading-account.model';

describe('Component Tests', () => {
  describe('TradingAccount Management Detail Component', () => {
    let comp: TradingAccountDetailComponent;
    let fixture: ComponentFixture<TradingAccountDetailComponent>;
    const route = ({ data: of({ tradingAccount: new TradingAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [TradingAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TradingAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TradingAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tradingAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
