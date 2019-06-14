/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrackstockTestModule } from '../../../test.module';
import { NamedPairsDetailComponent } from 'app/entities/named-pairs/named-pairs-detail.component';
import { NamedPairs } from 'app/shared/model/named-pairs.model';

describe('Component Tests', () => {
  describe('NamedPairs Management Detail Component', () => {
    let comp: NamedPairsDetailComponent;
    let fixture: ComponentFixture<NamedPairsDetailComponent>;
    const route = ({ data: of({ namedPairs: new NamedPairs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [NamedPairsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NamedPairsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NamedPairsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.namedPairs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
