import {Component, OnInit} from '@angular/core';
import {MedicineService} from "../../services/medicine.service";
import {Medicine} from "../../models/medicine";

@Component({
  selector: 'app-medicine',
  templateUrl: './medicine.component.html',
  styleUrls: ['./medicine.component.scss']
})
export class MedicineComponent implements OnInit {

  medicines: Medicine[] = [];
  totalItems = 0;
  page = 0;
  size = 10;
  protected readonly Math = Math;
  searchText: string = '';

  constructor(private medicineService: MedicineService) {
  }

  ngOnInit() {
    //this.loadMedicines();
    this.loadMockData();
  }

  loadMedicines() {
    this.medicineService.getMedicines(this.page, this.size).subscribe(res => {
      this.medicines = res.content;
      this.totalItems = res.totalElements;
    });
  }

  deleteMedicine(id: number) {
    if (confirm('Are you sure you want to delete this medicine?')) {
      this.medicineService.deleteMedicine(id).subscribe(() => this.loadMedicines());
    }
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

  private loadMockData() {
    this.medicines = [
      {
        id: 1,
        name: "Paracetamol",
        brand: "Panadol",
        batchNumber: "B001",
        expiryDate: "2025-12-15",
        price: 5.99,
        stock: 100,
        quantity: 100,
        prescriptionRequired: false,
        createdAt: "2025-12-26T10:00:00Z",
        updatedAt: "2025-12-26T10:00:00Z",
      }
    ];
    this.totalItems = 1;
  }
}
