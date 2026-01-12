import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor() { }

  stats = [
    { title: 'Total Customers', value: 120, icon: 'bi-people', color: 'primary' },
    { title: 'Manufacturers', value: 28, icon: 'bi-building', color: 'info' },
    { title: 'Medicines', value: 635, icon: 'bi-capsule', color: 'success' },
    { title: 'Out of Stock', value: 58, icon: 'bi-exclamation-triangle', color: 'danger' },
    { title: 'Expired Medicines', value: 3, icon: 'bi-calendar-x', color: 'warning' },
    { title: 'Total Invoices', value: 1024, icon: 'bi-receipt', color: 'secondary' }
  ];

  quickActions = [
    { label: 'POS Invoice', icon: 'bi-cart-plus', route: '/pos' },
    { label: 'New Invoice', icon: 'bi-file-earmark-text', route: '/invoice' },
    { label: 'Add Medicine', icon: 'bi-plus-circle', route: '/medicine/add' },
    { label: 'Add Customer', icon: 'bi-person-plus', route: '/customer/add' }
  ];

  reports = [
    { label: 'Sales Report', icon: 'bi-graph-up', route: '/reports/sales' },
    { label: 'Purchase Report', icon: 'bi-bag', route: '/reports/purchase' },
    { label: 'Stock Report', icon: 'bi-box-seam', route: '/reports/stock' },
    { label: 'Today’s Report', icon: 'bi-clock-history', route: '/reports/today' }
  ];

  ngOnInit(): void {
  }

}
