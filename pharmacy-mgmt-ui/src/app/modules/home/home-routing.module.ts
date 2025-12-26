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
      {
        path: 'invoice',
        loadChildren: () =>
          import('../invoice/invoice.module').then(m => m.InvoiceModule)
      },
      {
        path: 'customer',
        loadChildren: () =>
          import('../customer/customer.module').then(m => m.CustomerModule)
      },
      {
        path: 'medicine',
        loadChildren: () =>
          import('../medicine/medicine.module').then(m => m.MedicineModule)
      },
      {
        path: 'manufacturer',
        loadChildren: () =>
          import('../manufacturer/manufacturer.module').then(m => m.ManufacturerModule)
      },
      {
        path: 'purchase',
        loadChildren: () =>
          import('../purchase/purchase.module').then(m => m.PurchaseModule)
      },
      {
        path: 'stock',
        loadChildren: () =>
          import('../stock/stock.module').then(m => m.StockModule)
      },
      {
        path: 'report',
        loadChildren: () =>
          import('../report/report.module').then(m => m.ReportModule)
      },
      {
        path: 'return',
        loadChildren: () =>
          import('../return/return.module').then(m => m.ReturnModule)
      },
      {
        path: 'supplier',
        loadChildren: () =>
          import('../supplier/supplier.module').then(m => m.SupplierModule)
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
