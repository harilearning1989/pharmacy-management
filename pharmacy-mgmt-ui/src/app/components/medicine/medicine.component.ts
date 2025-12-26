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
        prescriptionRequired: false,
        createdAt: "2025-12-26T10:00:00Z",
        updatedAt: "2025-12-26T10:00:00Z",
      },
      {
        id: 2,
        name: "Ibuprofen",
        brand: "Advil",
        batchNumber: "B002",
        expiryDate: "2025-12-29",
        price: 8.5,
        stock: 50,
        prescriptionRequired: false,
        createdAt: "2025-12-26T10:05:00Z",
        updatedAt: "2025-12-26T10:05:00Z",
      },
      {
        id: 3,
        name: "Amoxicillin",
        brand: "Moxatag",
        batchNumber: "B003",
        expiryDate: "2025-12-31",
        price: 12.75,
        stock: 30,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:10:00Z",
        updatedAt: "2025-12-26T10:10:00Z",
      },
      {
        id: 4,
        name: "Cetirizine",
        brand: "Zyrtec",
        batchNumber: "B004",
        expiryDate: "2027-01-10",
        price: 6.2,
        stock: 80,
        prescriptionRequired: false,
        createdAt: "2025-12-26T10:15:00Z",
        updatedAt: "2025-12-26T10:15:00Z",
      },
      {
        id: 5,
        name: "Metformin",
        brand: "Glucophage",
        batchNumber: "B005",
        expiryDate: "2026-08-05",
        price: 15.0,
        stock: 40,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:20:00Z",
        updatedAt: "2025-12-26T10:20:00Z",
      },
      {
        id: 6,
        name: "Omeprazole",
        brand: "Prilosec",
        batchNumber: "B006",
        expiryDate: "2026-11-12",
        price: 10.5,
        stock: 60,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:25:00Z",
        updatedAt: "2025-12-26T10:25:00Z",
      },
      {
        id: 7,
        name: "Aspirin",
        brand: "Bayer",
        batchNumber: "B007",
        expiryDate: "2027-02-28",
        price: 4.99,
        stock: 120,
        prescriptionRequired: false,
        createdAt: "2025-12-26T10:30:00Z",
        updatedAt: "2025-12-26T10:30:00Z",
      },
      {
        id: 8,
        name: "Lisinopril",
        brand: "Zestril",
        batchNumber: "B008",
        expiryDate: "2026-09-18",
        price: 11.75,
        stock: 35,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:35:00Z",
        updatedAt: "2025-12-26T10:35:00Z",
      },
      {
        id: 9,
        name: "Simvastatin",
        brand: "Zocor",
        batchNumber: "B009",
        expiryDate: "2026-07-22",
        price: 14.0,
        stock: 45,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:40:00Z",
        updatedAt: "2025-12-26T10:40:00Z",
      },
      {
        id: 10,
        name: "Hydrochlorothiazide",
        brand: "Microzide",
        batchNumber: "B010",
        expiryDate: "2026-05-30",
        price: 9.5,
        stock: 55,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:45:00Z",
        updatedAt: "2025-12-26T10:45:00Z",
      },
      {
        id: 1,
        name: "Paracetamol",
        brand: "Panadol",
        batchNumber: "B001",
        expiryDate: "2026-03-15",
        price: 5.99,
        stock: 100,
        prescriptionRequired: false,
        createdAt: "2025-12-26T10:00:00Z",
        updatedAt: "2025-12-26T10:00:00Z",
      },
      {
        id: 2,
        name: "Ibuprofen",
        brand: "Advil",
        batchNumber: "B002",
        expiryDate: "2026-06-20",
        price: 8.5,
        stock: 50,
        prescriptionRequired: false,
        createdAt: "2025-12-26T10:05:00Z",
        updatedAt: "2025-12-26T10:05:00Z",
      },
      {
        id: 3,
        name: "Amoxicillin",
        brand: "Moxatag",
        batchNumber: "B003",
        expiryDate: "2025-12-31",
        price: 12.75,
        stock: 30,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:10:00Z",
        updatedAt: "2025-12-26T10:10:00Z",
      },
      {
        id: 4,
        name: "Cetirizine",
        brand: "Zyrtec",
        batchNumber: "B004",
        expiryDate: "2027-01-10",
        price: 6.2,
        stock: 80,
        prescriptionRequired: false,
        createdAt: "2025-12-26T10:15:00Z",
        updatedAt: "2025-12-26T10:15:00Z",
      },
      {
        id: 5,
        name: "Metformin",
        brand: "Glucophage",
        batchNumber: "B005",
        expiryDate: "2026-08-05",
        price: 15.0,
        stock: 40,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:20:00Z",
        updatedAt: "2025-12-26T10:20:00Z",
      },
      {
        id: 6,
        name: "Omeprazole",
        brand: "Prilosec",
        batchNumber: "B006",
        expiryDate: "2026-11-12",
        price: 10.5,
        stock: 60,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:25:00Z",
        updatedAt: "2025-12-26T10:25:00Z",
      },
      {
        id: 7,
        name: "Aspirin",
        brand: "Bayer",
        batchNumber: "B007",
        expiryDate: "2027-02-28",
        price: 4.99,
        stock: 120,
        prescriptionRequired: false,
        createdAt: "2025-12-26T10:30:00Z",
        updatedAt: "2025-12-26T10:30:00Z",
      },
      {
        id: 8,
        name: "Lisinopril",
        brand: "Zestril",
        batchNumber: "B008",
        expiryDate: "2026-09-18",
        price: 11.75,
        stock: 35,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:35:00Z",
        updatedAt: "2025-12-26T10:35:00Z",
      },
      {
        id: 9,
        name: "Simvastatin",
        brand: "Zocor",
        batchNumber: "B009",
        expiryDate: "2026-07-22",
        price: 14.0,
        stock: 45,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:40:00Z",
        updatedAt: "2025-12-26T10:40:00Z",
      },
      {
        id: 10,
        name: "Hydrochlorothiazide",
        brand: "Microzide",
        batchNumber: "B010",
        expiryDate: "2026-05-30",
        price: 9.5,
        stock: 55,
        prescriptionRequired: true,
        createdAt: "2025-12-26T10:45:00Z",
        updatedAt: "2025-12-26T10:45:00Z",
      },
    ];
    this.totalItems = 20;
  }
}
