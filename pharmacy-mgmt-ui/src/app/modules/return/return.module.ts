import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReturnRoutingModule } from './return-routing.module';
import { FormsModule } from '@angular/forms';
import { ReturnComponent } from 'src/app/components/return/return.component';
import { ViewSoldMedicineByCustomerComponent } from 'src/app/components/view-sold-medicine-by-customer/view-sold-medicine-by-customer.component';


@NgModule({
  declarations: [
    ReturnComponent,
    ViewSoldMedicineByCustomerComponent
  ],
  imports: [
    CommonModule,
    ReturnRoutingModule,
    FormsModule
  ]
})
export class ReturnModule { }
