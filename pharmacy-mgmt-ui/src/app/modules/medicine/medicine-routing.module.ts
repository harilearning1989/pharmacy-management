import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MedicineComponent} from "../../components/medicine/medicine.component";

const routes: Routes = [
  {
    path: '', component: MedicineComponent
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MedicineRoutingModule {
}
