import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Medicine } from 'src/app/models/medicine';
import { MedicineService } from 'src/app/services/medicine.service';
import { MedicineNameDetailComponent } from '../medicine-name-detail/medicine-name-detail.component';

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

  constructor(private medicineService: MedicineService,
    private modalService: NgbModal
  ) {
  }

  ngOnInit(): void {
    this.loadTotalMedicines()
  }

  loadTotalMedicines() {
    this.medicineService.loadTotalMedicines().subscribe(data => this.medicines = data);
  }

  loadAvailableMedicines() {
    this.medicineService.loadAvailableMedicines().subscribe(data => this.medicines = data);
  }

  loadExpiredMedicines() {
    this.medicineService.loadExpiredMedicines().subscribe(data => this.medicines = data);
  }

  loadOutOfStockMedicines() {
    this.medicineService.loadOutOfStockMedicines().subscribe(data => this.medicines = data);
  }

  setStatus(status: any) {
    this.statusFilter = status;
    this.medicines = [];

    switch (status) {
      case 'ALL':
        // e.g. this.loadAllItems();
        this.loadTotalMedicines();
        break;

      case 'AVAILABLE':
        // e.g. this.filterByStatus('AVAILABLE');
        this.loadAvailableMedicines();
        break;

      case 'OUT_OF_STOCK':
        // e.g. this.filterByStatus('OUT_OF_STOCK');
        this.loadOutOfStockMedicines();
        break;

      case 'EXPIRED':
        // e.g. this.filterByStatus('EXPIRED');
        this.loadExpiredMedicines();
        break;

      default:
        console.warn('Unknown status:', status);
        this.loadTotalMedicines();
        break;
    }

  }

  openEditModal(name: string) {
    const modalRef = this.modalService.open(MedicineNameDetailComponent, {
        size: 'lg',
        backdrop: 'static'
      });

      // Pass data to modal
      modalRef.componentInstance.medicineName = name;
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

  isExpired(expiryDate: string): boolean {
    return new Date(expiryDate) < new Date();
  }

  getExpiryClass(expiryDate: string,stock: number): string {
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const expiry = new Date(expiryDate);
    expiry.setHours(0, 0, 0, 0);

    const diffInDays =
      (expiry.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

    if (diffInDays < 0 || stock == 0) {
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
