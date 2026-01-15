import { Component, OnInit } from '@angular/core';
import { catchError, forkJoin, of } from 'rxjs';
import { MedicineCountDto } from 'src/app/models/medicine-count-dto';
import { CustomerService } from 'src/app/services/customer.service';
import { MedicineService } from 'src/app/services/medicine.service';
import { SaleService } from 'src/app/services/sale.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  medicineCountDto!: MedicineCountDto;
  customerCount: number = 0;
  soldMedicineCount: number = 0;

  constructor(
    private medicineService: MedicineService,
    private customerService: CustomerService,
    private saleService: SaleService
  ) { }

  stats = [
    { title: 'Total Customers', value: 0, icon: 'bi-people', color: 'primary' },
    { title: 'Total Medicines', value: 0, icon: 'bi-box-seam', color: 'primary' },
    { title: 'Available Medicines', value: 0, icon: 'bi-check-circle', color: 'success' },
    { title: 'Out of Stock Medicines', value: 0, icon: 'bi-exclamation-triangle', color: 'danger' },
    { title: 'Expired Medicines', value: 0, icon: 'bi-calendar-x', color: 'warning' },
    { title: 'Total Sold Medicines', value: 0, icon: 'bi-receipt', color: 'secondary' }
  ];

  quickActions = [
    { label: 'Sale', icon: 'bi-cash-coin', route: '../sales' },
    { label: 'Invoice', icon: 'bi-receipt-cutoff', route: '../invoice' },
    { label: 'Medicine', icon: 'bi-capsule-pill', route: '../medicine' },
    { label: 'Stock', icon: 'bi-box-seam', route: '../stock' },
    { label: 'Customer', icon: 'bi-people', route: '../customer' },
    { label: 'Report', icon: 'bi-bar-chart-line', route: '../report' },
    { label: 'Supplier', icon: 'bi-truck', route: '../supplier' }
  ];

  reports = [
    { label: 'Sales Report', icon: 'bi-graph-up', route: '/reports/sales' },
    { label: 'Purchase Report', icon: 'bi-bag', route: '/reports/purchase' },
    { label: 'Stock Report', icon: 'bi-box-seam', route: '/reports/stock' },
    { label: 'Today’s Report', icon: 'bi-clock-history', route: '/reports/today' }
  ];

  ngOnInit(): void {
    this.loadCountDetails();
  }

  loadCountDetails() {
    forkJoin({
      medicine: this.medicineService.loadMedicineCountDetails().pipe(
        catchError(err => {
          console.error('Medicine API failed', err);
          return of({
            totalMedicines: 0,
            availableMedicines: 0,
            outOfStockMedicines: 0,
            expiredMedicines: 0
          } as MedicineCountDto);
        })
      ),

      customerCount: this.customerService.getCustomerCount().pipe(
        catchError(err => {
          console.error('Customer API failed', err);
          return of(0); // fallback number
        })
      ),

      soldMedicineCount: this.saleService.getSoldMedicineCount().pipe(
        catchError(err => {
          console.error('Sold medicine API failed', err);
          return of(0); // fallback number
        })
      )
    }).subscribe({
      next: ({ medicine, customerCount, soldMedicineCount }) => {
        this.updateStats(medicine, customerCount, soldMedicineCount);
      }
    });
  }

  updateStats(
    medicine: MedicineCountDto,
    customerCount: number,
    soldMedicineCount: number
  ): void {
    this.stats = [
      {
        title: 'Total Customers',
        value: customerCount, // ✅ direct number
        icon: 'bi-people',
        color: 'primary'
      },
      {
        title: 'Total Medicines',
        value: medicine.totalMedicines,
        icon: 'bi-box-seam',
        color: 'primary'
      },
      {
        title: 'Available Medicines',
        value: medicine.availableMedicines,
        icon: 'bi-check-circle',
        color: 'success'
      },
      {
        title: 'Out of Stock Medicines',
        value: medicine.outOfStockMedicines,
        icon: 'bi-exclamation-triangle',
        color: 'danger'
      },
      {
        title: 'Expired Medicines',
        value: medicine.expiredMedicines,
        icon: 'bi-calendar-x',
        color: 'warning'
      },
      {
        title: 'Total Sold Medicines',
        value: soldMedicineCount,
        icon: 'bi-receipt',
        color: 'secondary'
      }
    ];
  }

}
