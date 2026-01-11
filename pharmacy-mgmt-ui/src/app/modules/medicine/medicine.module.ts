import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {MedicineRoutingModule} from './medicine-routing.module';
import {MedicineComponent} from "../../components/medicine/medicine.component";
import {FormsModule} from "@angular/forms";
import { ConfirmDialogComponent } from 'src/app/components/shared/confirm-dialog/confirm-dialog.component';


@NgModule({
  declarations: [MedicineComponent,ConfirmDialogComponent],
  imports: [
    CommonModule,
    MedicineRoutingModule,
    FormsModule
  ]
})
export class MedicineModule {
}
