import { Component, OnInit } from '@angular/core';
import { MedicineService } from "../../services/medicine.service";
import { Medicine } from "../../models/medicine";

@Component({
  selector: 'app-medicine',
  templateUrl: './medicine.component.html',
  styleUrls: ['./medicine.component.scss']
})
export class MedicineComponent implements OnInit {

  medicines: Medicine[] = [];
  searchText: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  sortColumn: string = '';
  saleId: number = 0;
  selectedMedicine: any;
  customerToDelete: any;
  deleteAction!: () => void;
  medicineName: string = '';

  constructor(private medicineService: MedicineService) {
  }

  ngOnInit() {
    this.loadMedicines();
  }

  loadMedicines() {
    this.medicineService.getAllMedicines().subscribe(res => {
      this.medicines = res;
    });
  }

  openEditDialog(medicine: Medicine) {
    this.selectedMedicine = medicine;
  }

  updateMedicine() {
    console.log('Updated Customer:', this.selectedMedicine);

    // Call API or update list here

    // Close modal manually
    const modal = document.getElementById('editCustomerModal');
    const modalInstance = (window as any).bootstrap.Modal.getInstance(modal);
    modalInstance.hide();
  }

  openDeleteModal(medicine: Medicine) {
    this.medicineName = medicine.name;
    this.deleteAction = () => this.deleteMedicine(medicine.id);
  }

  deleteConfirmed() {
    this.deleteAction?.();
  }

  deleteMedicine(medicineId: number) {
    console.log('deleteMedicine::', medicineId);
    this.medicineService.deleteMedicine(medicineId).subscribe({
      next: () => {
        // update UI list
        this.medicines = this.medicines.filter(m => m.id !== medicineId);

        // close modal
        this.closeModal('deleteConfirmModal');
      },
      error: () => alert('Failed to delete medicine')
    });
    this.closeModal('deleteCustomerModal');
  }

  private closeModal(modalId: string) {
    const modalEl = document.getElementById(modalId);
    const modal = (window as any).bootstrap.Modal.getInstance(modalEl);
    modal?.hide();
  }

  onCSVUpload(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.medicineService.uploadCSV(file).subscribe(() => this.loadMedicines());
    }
  }

  filteredMedicines() {
    if (!this.searchText) {
      return this.medicines;
    }

    const text = this.searchText.toLowerCase();

    return this.medicines.filter(med =>
      med.name.toLowerCase().includes(text) ||
      med.brand.toLowerCase().includes(text) ||
      med.expiryDate.includes(text) ||
      med.batchNumber.toLowerCase().includes(text)
    );
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
