import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ManufacturerComponent} from "../../components/manufacturer/manufacturer.component";

const routes: Routes = [
  {
    path: '', component: ManufacturerComponent
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManufacturerRoutingModule { }
