/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrackstockTestModule } from '../../../test.module';
import { NamedPairsComponent } from 'app/entities/named-pairs/named-pairs.component';
import { NamedPairsService } from 'app/entities/named-pairs/named-pairs.service';
import { NamedPairs } from 'app/shared/model/named-pairs.model';

describe('Component Tests', () => {
  describe('NamedPairs Management Component', () => {
    let comp: NamedPairsComponent;
    let fixture: ComponentFixture<NamedPairsComponent>;
    let service: NamedPairsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrackstockTestModule],
        declarations: [NamedPairsComponent],
        providers: []
      })
        .overrideTemplate(NamedPairsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NamedPairsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NamedPairsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NamedPairs(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.namedPairs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
