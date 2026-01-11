import { Component, OnInit } from '@angular/core';
import { Medicine } from 'src/app/models/medicine';
import { MedicineService } from 'src/app/services/medicine.service';

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.scss']
})
export class StockComponent implements OnInit {

  medicines: Medicine[] = [];
  searchText: string = '';
  statusFilter: 'ALL' | 'AVAILABLE' | 'OUT_OF_STOCK' | 'EXPIRED' = 'ALL';
  sortDirection: 'asc' | 'desc' = 'asc';
  sortColumn: string = '';

  constructor(private medicineService: MedicineService) {
  }

  ngOnInit(): void {
    this.loadAvailableStock();
  }

  loadAvailableStock() {
    this.medicineService.loadAvailableStock().subscribe(data => this.medicines = data);
  }

  setStatus(status: any) {
    this.statusFilter = status;
  }

  /*
  

  addStock() {
    const modalRef = this.modalService.open(StockFormComponent);
    modalRef.componentInstance.mode = 'Add';
    modalRef.result.then((result: any) => {
      if (result) this.loadStocks();
    });
  }

  editStock(stock: Stock) {
    const modalRef = this.modalService.open(StockFormComponent);
    modalRef.componentInstance.mode = 'Edit';
    modalRef.componentInstance.stock = {...stock};
    modalRef.result.then((result: any) => {
      if (result) this.loadStocks();
    });
  }

  deleteStock(id: number) {
    if (confirm('Are you sure you want to delete this stock?')) {
      this.stockService.deleteStock(id).subscribe(() => this.loadStocks());
    }
  }

  */

  filteredMedicines() {
    if (!this.searchText) {
      return this.medicines;
    }

    const text = this.searchText.toLowerCase();

    return this.medicines.filter((sale: Medicine) =>
      sale.name.toLowerCase().includes(text) ||
      sale.brand.toLowerCase().includes(text) ||
      sale.batchNumber.toLowerCase().includes(text)
    );
  }

  get filteredMedicines1() {
    return this.medicines.filter(m => {

      // 🔍 Search filter
      const searchMatch =
        !this.searchText ||
        m.name?.toLowerCase().includes(this.searchText.toLowerCase()) ||
        m.brand?.toLowerCase().includes(this.searchText.toLowerCase()) ||
        m.batchNumber?.toLowerCase().includes(this.searchText.toLowerCase());

      // 📦 Status filter
      let statusMatch = true;

      if (this.statusFilter === 'AVAILABLE') {
        statusMatch = m.stock > 0 && !this.isExpired(m.expiryDate);
      }

      if (this.statusFilter === 'OUT_OF_STOCK') {
        statusMatch = m.stock === 0;
      }

      if (this.statusFilter === 'EXPIRED') {
        statusMatch = this.isExpired(m.expiryDate);
      }

      return searchMatch && statusMatch;
    });
  }

  isExpired(expiryDate: string): boolean {
    return new Date(expiryDate) < new Date();
  }



  getExpiryClass(expiryDate: string): string {
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const expiry = new Date(expiryDate);
    expiry.setHours(0, 0, 0, 0);

    const diffInDays =
      (expiry.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

    if (diffInDays < 0) {
      return 'expired-row'; // red
    }

    if (diffInDays <= 7) {
      return 'near-expiry-row'; // yellow
    }

    return '';
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

    this.medicines.sort((a: any, b: any) => {
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
