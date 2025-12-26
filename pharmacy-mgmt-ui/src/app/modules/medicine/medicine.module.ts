import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {MedicineRoutingModule} from './medicine-routing.module';
import {MedicineComponent} from "../../components/medicine/medicine.component";
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [MedicineComponent],
  imports: [
    CommonModule,
    MedicineRoutingModule,
    FormsModule
  ]
})
export class MedicineModule {
}
