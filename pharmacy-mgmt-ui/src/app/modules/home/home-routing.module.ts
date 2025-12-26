import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "../../components/home/home.component";

const routes: Routes = [
  {
    path: '', component: HomeComponent,
    children: [
      {
        path: 'dashboard',
        loadChildren: () =>
          import('../../modules/dashboard/dashboard.module')
            .then(m => m.DashboardModule)
      },
      {path: '', redirectTo: 'dashboard', pathMatch: 'full'}
    ]
  } // default dashboard route
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule {
}
