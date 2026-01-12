import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Medicine } from 'src/app/models/medicine';
import { MedicineService } from 'src/app/services/medicine.service';

@Component({
  selector: 'app-medicine-name-detail',
  templateUrl: './medicine-name-detail.component.html',
  styleUrls: ['./medicine-name-detail.component.scss']
})
export class MedicineNameDetailComponent implements OnInit {

  @Input() medicineName: string = '';
  medicines: Medicine[] = [];
  searchText: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  sortColumn: string = '';

  constructor(public activeModal: NgbActiveModal,
    private medicineService: MedicineService
  ) {}

  ngOnInit(): void {
    this.loadMedicinesByName();
  }

  loadMedicinesByName() {
    this.medicineService.loadMedicinesByName(this.medicineName).subscribe(data => this.medicines = data);
  }

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
