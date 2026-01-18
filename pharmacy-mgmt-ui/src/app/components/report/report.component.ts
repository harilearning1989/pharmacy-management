import { Component, OnInit } from '@angular/core';
import { SaleHistory } from 'src/app/models/sale-history';
import { SalesSummaryResponseDto } from 'src/app/models/sales-summary-response-dto';
import { SaleService } from 'src/app/services/sale.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  searchText: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  sortColumn: string = '';
  saleHistory: SaleHistory[] = [];
  selected: 'today' | 'week' | 'month' = 'today';
  salesSummary!: SalesSummaryResponseDto;

  constructor(private saleService: SaleService) { }

  filters = {
    fromDate: '',
    toDate: '',
    reportType: 'SALES',
    paymentMethod: ''
  };

  kpis = [
    { title: 'Total Sales', value: 125, icon: 'bi-receipt', color: 'primary' },
    { title: 'Total Revenue', value: '₹2,45,000', icon: 'bi-currency-rupee', color: 'success' },
    { title: 'Total Discount', value: '₹12,000', icon: 'bi-percent', color: 'warning' },
    { title: 'GST Collected', value: '₹9,800', icon: 'bi-bank', color: 'info' }
  ];

  sales = [
    {
      invoiceNo: 'INV-1023',
      date: '2024-01-10',
      customer: 'John Doe',
      payment: 'CASH',
      total: 1240
    }
  ];

  ngOnInit(): void {
    this.loadAllSales();
  }

  loadAllSales() {
    this.saleService.loadAllSales().subscribe(res => {
      this.saleHistory = res;
    });
  }

  applyFilters() {
    console.log('Filters applied', this.filters);
  }

  export(format: string) {
    console.log('Export:', format);
  }

  loadSummary(type: 'today' | 'week' | 'month'): void {
    this.selected = type;

    this.saleService.getSummary(type).subscribe({
      next: (data) => {
        console.log('Sales summary:', data);
        //this.saleHistory = data;
        this.salesSummary = data;
        this.saleHistory = data.sales;
        // TODO: bind data to UI
      },
      error: (err) => console.error(err)
    });
  }

  filterCustomer() {
      if (!this.searchText) {
        return this.saleHistory;
      }
  
      const text = this.searchText.toLowerCase();
  
      return this.saleHistory.filter((sale: SaleHistory) =>
        sale.customerName.toLowerCase().includes(text) ||
        sale.customerPhone.toLowerCase().includes(text) ||
        sale.saleDate.toLowerCase().includes(text) ||
        sale.username.toLowerCase().includes(text)
      );
    }
  
    sort(column: string) {
      if (this.sortColumn === column) {
        // toggle direction
        this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
      } else {
        // new column sort
        this.sortColumn = column;
        this.sortDirection = 'asc';
      }
  
      this.saleHistory.sort((a: any, b: any) => {
        let valueA = a[column];
        let valueB = b[column];
  
        // Handle string sorting
        if (typeof valueA === 'string') {
          valueA = valueA.toLowerCase();
          valueB = valueB.toLowerCase();
        }
  
        if (valueA < valueB) return this.sortDirection === 'asc' ? -1 : 1;
        if (valueA > valueB) return this.sortDirection === 'asc' ? 1 : -1;
        return 0;
      });
    }
  
    getSortIcon(column: string): string {
      if (this.sortColumn !== column) {
        return 'bi-arrow-down-up'; // default
      }
      return this.sortDirection === 'asc'
        ? 'bi-sort-alpha-down'
        : 'bi-sort-alpha-up';
    }

}
