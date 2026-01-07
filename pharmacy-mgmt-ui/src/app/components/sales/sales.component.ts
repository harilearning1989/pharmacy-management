import {Component, HostListener, OnInit} from '@angular/core';
import {CustomerService} from "../../services/customer.service";
import {allCustomers, Customer} from "../../models/customer";
import {Medicine, medicines} from "../../models/medicine";
import {SaleService} from "../../services/sale.service";

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
  searchName: string = '';
  searchPhone: string = '';
  selectedCustomer: Customer | null = null;
  selectedMedicineTmp: (Medicine & { qty?: number; totalPrice?: number }) | null = null;
  searchText = '';
  stock = 120;
  expiry = '2023-08-15';

  filteredMedicines: Medicine[] = [];

  // ===== PAYMENT SUMMARY VALUES =====
  subtotal = 0;
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
    this.searchName = customer.name;
    this.searchPhone = customer.phone;
    this.customers = [];
    this.activeInput = null;
  }

  onNameChange(event: any) {
    this.activeInput = 'name';
    this.searchName = event.target.value.trim();
    this.searchPhone = '';
    this.handleSearch();
  }

  onPhoneChange(event: any) {
    this.activeInput = 'phone';
    this.searchPhone = event.target.value.trim();
    this.searchName = '';
    this.handleSearch();
  }

  handleSearch() {
    const query =
      this.searchName.length >= 3 || this.searchPhone.length >= 3;

    if (!query) {
      this.customers = [];
      return;
    }

    this.searchCustomersApi();
  }

  searchCustomersApi() {
    const payload = {
      name: this.searchName || null,
      phone: this.searchPhone || null
    };

    this.customerService.searchCustomers(payload).subscribe({
      next: (res: any) => {
        this.customers = res.data || [];
      },
      error: () => {
        //this.customers = [];
        this.customers = allCustomers.filter(c =>
          c.phone.includes(this.searchPhone))
      }
    });
  }

  onSearch() {
    this.filteredMedicines = this.searchText
      ? medicines.filter(m =>
        m.name.toLowerCase().includes(this.searchText.toLowerCase())
      )
      : [];
  }

  selectMedicine(medicine: Medicine) {
    this.searchText = medicine.name;
    this.filteredMedicines = [];
    this.selectedMedicineTmp = {
      ...medicine,
      qty: 1,   // 👈 THIS is mandatory
      totalPrice: medicine.price   // qty(1) * price
    };
  }

  onQtyChange(qty: number) {
    if (!this.selectedMedicineTmp) {
      return;
    }

    const stock = this.selectedMedicineTmp.stock;

    if (!qty || qty < 1) {
      qty = 1;
    }

    if (qty > stock) {
      qty = stock;
    }

    this.selectedMedicineTmp.qty = qty;
    this.selectedMedicineTmp.totalPrice = qty * this.selectedMedicineTmp.price;
  }


  // ADD TO CART (PREVENT DUPLICATE BATCH)
  addToCart() {
    if (!this.selectedMedicineTmp || !this.selectedMedicineTmp.qty) return;

    const med = this.selectedMedicineTmp;

    // 🔁 Check existing batch
    const existing = this.cartItems.find(
      item => item.batchNumber === med.batchNumber
    );

    if (existing) {
      // ✅ Increment quantity
      existing.qty = (existing.qty ?? 0) + med.qty;

      // ✅ Recalculate total
      existing.totalPrice =
        existing.qty * existing.price;
    } else {
      // ✅ Add new item
      this.cartItems.push({
        ...med,
        qty: med.qty,
        totalPrice: med.qty * med.price
      });
    }

    this.calculateSummary();
    // 🧹 Clear Add Medicine section
    this.selectedMedicineTmp = null;
    this.searchText = '';
    this.filteredMedicines = [];
  }

  // Remove item
  removeItem(index: number) {
    this.cartItems.splice(index, 1);
    this.calculateSummary();
  }

  // ===== PAYMENT SUMMARY CALCULATION =====
  calculateSummary() {
    this.subtotal = this.cartItems.reduce(
      (sum, item) => sum + (item.totalPrice ?? 0),
      0
    );

    // Example: flat discount or change logic
    this.discount = this.subtotal > 300 ? 15 : 0;

    this.gst = (this.subtotal - this.discount) * this.GST_PERCENT / 100;

    this.grandTotal =
      this.subtotal - this.discount + this.gst;
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
        qty: item.qty!,
        price: item.price,
        total: item.totalPrice!
      })),

      subtotal: this.subtotal,
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

        this.subtotal = 0;
        this.discount = 0;
        this.gst = 0;
        this.grandTotal = 0;
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
