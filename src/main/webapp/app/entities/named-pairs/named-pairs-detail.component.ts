import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INamedPairs } from 'app/shared/model/named-pairs.model';

@Component({
  selector: 'jhi-named-pairs-detail',
  templateUrl: './named-pairs-detail.component.html'
})
export class NamedPairsDetailComponent implements OnInit {
  namedPairs: INamedPairs;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ namedPairs }) => {
      this.namedPairs = namedPairs;
    });
  }

  previousState() {
    window.history.back();
  }
}
