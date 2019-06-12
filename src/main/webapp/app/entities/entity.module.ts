import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'trading-account',
        loadChildren: './trading-account/trading-account.module#TrackstockTradingAccountModule'
      },
      {
        path: 'shipping-details',
        loadChildren: './shipping-details/shipping-details.module#TrackstockShippingDetailsModule'
      },
      {
        path: 'post',
        loadChildren: './post/post.module#TrackstockPostModule'
      },
      {
        path: 'video-post',
        loadChildren: './video-post/video-post.module#TrackstockVideoPostModule'
      },
      {
        path: 'comment',
        loadChildren: './comment/comment.module#TrackstockCommentModule'
      },
      {
        path: 'watchlist',
        loadChildren: './watchlist/watchlist.module#TrackstockWatchlistModule'
      },
      {
        path: 'instrument',
        loadChildren: './instrument/instrument.module#TrackstockInstrumentModule'
      },
      {
        path: 'position',
        loadChildren: './position/position.module#TrackstockPositionModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrackstockEntityModule {}
