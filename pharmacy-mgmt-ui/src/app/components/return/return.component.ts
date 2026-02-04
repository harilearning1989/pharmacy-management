import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SaleHistory } from 'src/app/models/sale-history';
import { SaleService } from 'src/app/services/sale.service';
import { ViewSoldMedicineByCustomerComponent } from '../view-sold-medicine-by-customer/view-sold-medicine-by-customer.component';

@Component({
  selector: 'app-return',
  templateUrl: './return.component.html',
  styleUrls: ['./return.component.scss']
})
export class ReturnComponent implements OnInit {

  searchText: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  sortColumn: string = '';
  saleHistory: SaleHistory[] = [];
  saleId: number = 0;

  constructor(private saleService: SaleService,
    private ngbModal: NgbModal
  ) {
  }

  ngOnInit(): void {
    this.loadAllSales();
  }

  loadAllSales() {
    this.saleService.loadAllSales().subscribe(res => {
      this.saleHistory = res;
    });
  }

  openSaleDialog(saleId: number) {
    const modalRef = this.ngbModal.open(ViewSoldMedicineByCustomerComponent, {
      size: 'xl',
      backdrop: 'static'
    });

    modalRef.componentInstance.saleId = saleId; // 👈 pass data
    modalRef.componentInstance.isEditMode = true;

    //this.loadAllSales();
    modalRef.result
      .then(result => {
        if (result === 'REFRESH') {
          this.loadAllSales(); // 🔁 refresh main component
        }
      })
      .catch(() => {
        // dismissed — do nothing
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
