import { Component, HostListener, OnInit } from '@angular/core';
import { CustomerService } from "../../services/customer.service";
import { allCustomers, Customer } from "../../models/customer";
import { Medicine, medicines } from "../../models/medicine";
import { SaleService } from "../../services/sale.service";

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.scss']
})
export class SalesComponent implements OnInit {

  ngOnInit(): void {
  }

  paymentMethod = 'CASH';

  activeInput: 'name' | 'phone' | null = null;
  cartItems: Medicine[] = [];
  customers: Customer[] = [];
  searchCustomerName: string = '';
  searchPhone: string = '';
  selectedCustomer: Customer | null = null;
  selectedMedicineTmp: (Medicine & { quantity?: number; totalPrice?: number }) | null = null;
  searchMedicineName = '';
  stock = 120;
  expiry = '2023-08-15';

  filteredMedicines: Medicine[] = [];

  // ===== PAYMENT SUMMARY VALUES =====
  subTotal = 0;
  discount = 0;
  gst = 0;
  grandTotal = 0;

  GST_PERCENT = 5; // example 5%

  constructor(private customerService: CustomerService,
    private saleService: SaleService) {
  }

  // ✅ Close dropdown when clicking outside
  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    const target = event.target as HTMLElement;
    const autocomplete = document.querySelector('.autocomplete-box');
    const inputBox = document.querySelector('input');

    if (autocomplete && inputBox && !autocomplete.contains(target) && !inputBox.contains(target)) {
      this.customers = [];
    }
  }

  selectCustomer(customer: any) {
    this.selectedCustomer = customer;
    this.searchCustomerName = customer.name;
    this.searchPhone = customer.phone;
    this.customers = [];
    this.activeInput = null;
  }

  onCustomerNameChange(event: any) {
    this.activeInput = 'name';
    this.searchCustomerName = event.target.value.trim();
    this.searchPhone = '';
    this.handleSearch();
  }

  onPhoneChange(event: any) {
    this.activeInput = 'phone';
    this.searchPhone = event.target.value.trim();
    this.searchCustomerName = '';

    if (this.searchPhone.length < 3) {
      this.customers = [];
      return;
    }

    this.customerService.searchByPhone(this.searchPhone).subscribe({
      next: (response: Customer[]) => {
        this.customers = response || [];
      },
      error: () => {
        this.customers = [];
      }
    });

  }

  handleSearch() {
    const query =
      this.searchCustomerName.length >= 3;

    if (!query) {
      this.customers = [];
      return;
    }

    this.customerService.searchCustomers(this.searchCustomerName).subscribe({
      next: (response: Customer[]) => {
        this.customers = response || [];
      },
      error: () => {
        this.customers = [];
      }
    });
  }

  onSearchMedicine() {
    this.filteredMedicines = this.searchMedicineName
      ? medicines.filter(m =>
        m.name.toLowerCase().includes(this.searchMedicineName.toLowerCase())
      )
      : [];
  }

  selectMedicine(medicine: Medicine) {
    this.searchMedicineName = medicine.name;
    this.filteredMedicines = [];
    this.selectedMedicineTmp = {
      ...medicine,
      quantity: 1,   // 👈 THIS is mandatory
      totalPrice: medicine.price   // quantity(1) * price
    };
  }

  onQtyChange(quantity: number) {
    if (!this.selectedMedicineTmp) {
      return;
    }

    const stock = this.selectedMedicineTmp.stock;

    if (!quantity || quantity < 1) {
      quantity = 1;
    }

    if (quantity > stock) {
      quantity = stock;
    }

    this.selectedMedicineTmp.quantity = quantity;
    this.selectedMedicineTmp.totalPrice = quantity * this.selectedMedicineTmp.price;
  }


  // ADD TO CART (PREVENT DUPLICATE BATCH)
  addToCart() {
    if (!this.selectedMedicineTmp || !this.selectedMedicineTmp.quantity) return;

    const med = this.selectedMedicineTmp;

    // 🔁 Check existing batch
    const existing = this.cartItems.find(
      item => item.batchNumber === med.batchNumber
    );

    if (existing) {
      // ✅ Increment quantity
      existing.quantity = (existing.quantity ?? 0) + med.quantity;

      // ✅ Recalculate total
      existing.totalPrice =
        existing.quantity * existing.price;
    } else {
      // ✅ Add new item
      this.cartItems.push({
        ...med,
        quantity: med.quantity,
        totalPrice: med.quantity * med.price
      });
    }

    this.calculateSummary();
    // 🧹 Clear Add Medicine section
    this.selectedMedicineTmp = null;
    this.searchMedicineName = '';
    this.filteredMedicines = [];
  }

  // Remove item
  removeItem(index: number) {
    this.cartItems.splice(index, 1);
    this.calculateSummary();
  }

  // ===== PAYMENT SUMMARY CALCULATION =====
  calculateSummary() {
    this.subTotal = this.cartItems.reduce(
      (sum, item) => sum + (item.totalPrice ?? 0),
      0
    );

    // Example: flat discount or change logic
    this.discount = this.subTotal > 300 ? 15 : 0;

    this.gst = (this.subTotal - this.discount) * this.GST_PERCENT / 100;

    this.grandTotal =
      this.subTotal - this.discount + this.gst;
  }

  // 🔥 PROCESS SALE CLICK
  processSale() {

    if (!this.selectedCustomer || !this.cartItems.length) {
      alert('Customer or cart is empty');
      return;
    }

    const payload = {
      customerId: this.selectedCustomer.id,

      items: this.cartItems.map(item => ({
        medicineId: item.id,
        batchNumber: item.batchNumber,
        quantity: item.quantity!,
        price: item.price,
        total: item.totalPrice!
      })),

      subTotal: this.subTotal,
      discount: this.discount,
      gst: this.gst,
      grandTotal: this.grandTotal,
      paymentMethod: this.paymentMethod
    };

    this.saleService.saveSale(payload).subscribe({
      next: () => {
        alert('Sale completed successfully');

        // 🧹 CLEAR EVERYTHING AFTER SAVE
        this.cartItems = [];
        this.selectedCustomer = null;
        this.selectedMedicineTmp = null;

        this.subTotal = 0;
        this.discount = 0;
        this.gst = 0;
        this.grandTotal = 0;
        this.searchCustomerName = '';
        this.searchPhone = '';
      },
      error: err => {
        console.error(err);
        alert('Failed to process sale');
      }
    });
  }

  getExpiryClass(expiryDate: string): string {
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const expDate = new Date(expiryDate);
    expDate.setHours(0, 0, 0, 0);

    const diffInDays =
      (expDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

    if (diffInDays < 0) {
      return 'table-danger'; // expired → red
    }

    if (diffInDays <= 7) {
      return 'table-warning'; // expiring within a week → yellow
    }

    return ''; // normal
  }

}
