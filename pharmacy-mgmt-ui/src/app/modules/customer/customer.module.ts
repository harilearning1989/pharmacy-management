import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CustomerRoutingModule} from './customer-routing.module';
import {CustomerComponent} from "../../components/customer/customer.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { GenderIconPipe } from 'src/app/pipes/gender-icon.pipe';


@NgModule({
  declarations: [
    CustomerComponent,
    GenderIconPipe
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    FormsModule,
    ReactiveFormsModule
]
})
export class CustomerModule {
}
