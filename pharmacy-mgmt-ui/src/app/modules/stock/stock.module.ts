import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {StockRoutingModule} from './stock-routing.module';
import {StockComponent} from "../../components/stock/stock.component";
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    StockComponent
  ],
  imports: [
    CommonModule,
    StockRoutingModule,
    FormsModule,
    NgbModule
  ]
})
export class StockModule {
}
