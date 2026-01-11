import { Component, OnInit } from '@angular/core';
import { SaleHistory } from 'src/app/models/sale-history';
import { SaleService } from 'src/app/services/sale.service';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.scss']
})
export class InvoiceComponent implements OnInit {

  searchText: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  sortColumn: string = '';
  saleHistory: SaleHistory[] = [];
  saleId: number = 0;

  constructor(private saleService: SaleService) {
  }

  ngOnInit(): void {
    this.loadAllSales();
  }

  loadAllSales() {
    this.saleService.loadAllSales().subscribe(res => {
      this.saleHistory = res;
    });
  }

  openSaleDialog(id: number) {
    this.saleId = id;

    const modal = new (window as any).bootstrap.Modal(
      document.getElementById('showSaleDetailModal')
    );
    modal.show();
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

  /**
   * Print invoice with styles preserved
   */
  printInvoice() {
    const printContents = document.getElementById('invoice-section')?.innerHTML;
    if (!printContents) return;

    const popup = window.open('', '_blank', 'width=900,height=700');
    if (popup) {
      // Copy all styles (Bootstrap + component CSS)
      const styles = Array.from(document.querySelectorAll('link[rel="stylesheet"], style'))
        .map((s) => s.outerHTML)
        .join('\n');

      popup.document.open();
      popup.document.write(`
        <html>
          <head>
            <title>Sale Invoice</title>
            ${styles}
            <style>
              body { margin: 20px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
              table { page-break-inside: auto; }
              tr { page-break-inside: avoid; page-break-after: auto; }
              thead { display: table-header-group; }
              tfoot { display: table-footer-group; }
              @media print { .btn, .modal-footer { display: none !important; } }
            </style>
          </head>
          <body onload="window.print(); window.close();">
            ${printContents}
          </body>
        </html>
      `);
      popup.document.close();
    }
  }

  // Email invoice
  emailInvoice() {
    if (!this.saleHistory) return;
    this.saleService.emailInvoice(this.saleId).subscribe({
      next: () => alert('Invoice emailed successfully'),
      error: () => alert('Failed to send email')
    });
  }

}
