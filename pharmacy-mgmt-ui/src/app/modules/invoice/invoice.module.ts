import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {InvoiceRoutingModule} from './invoice-routing.module';
import {InvoiceComponent} from "../../components/invoice/invoice.component";
import {FormsModule} from "@angular/forms";
import { SaleDialogDetailsComponent } from 'src/app/components/sale-dialog-details/sale-dialog-details.component';


@NgModule({
  declarations: [
    InvoiceComponent,
    SaleDialogDetailsComponent
  ],
  imports: [
    CommonModule,
    InvoiceRoutingModule,
    FormsModule
  ]
})
export class InvoiceModule {
}
