import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {InvoiceRoutingModule} from './invoice-routing.module';
import {InvoiceComponent} from "../../components/invoice/invoice.component";
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    InvoiceComponent
  ],
  imports: [
    CommonModule,
    InvoiceRoutingModule,
    FormsModule
  ]
})
export class InvoiceModule {
}
