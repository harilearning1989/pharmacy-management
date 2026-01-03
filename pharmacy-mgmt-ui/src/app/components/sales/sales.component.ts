import {Component, HostListener, OnInit} from '@angular/core';
import {CustomerService} from "../../services/customer.service";
import {allCustomers, Customer} from "../../models/customer";
import {Medicine, medicines} from "../../models/medicine";

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.scss']
})
export class SalesComponent implements OnInit {

  constructor(private customerService: CustomerService) {
  }

  ngOnInit(): void {
  }

  activeInput: 'name' | 'phone' | null = null;

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

  selectedMedicine = {
    name: 'Paracetamol 500mg',
    batch: 'BATCH123',
    expiry: '15/08/2026',
    mrp: 150,
    stock: 120
  };

  quantity = 1;
  cart: any[] = [];

  subtotal = 0;
  tax = 0;
  grandTotal = 0;

  addToCart() {
    const total = this.selectedMedicine.mrp * this.quantity;
    this.cart.push({
      name: this.selectedMedicine.name,
      batch: this.selectedMedicine.batch,
      expiry: this.selectedMedicine.expiry,
      qty: this.quantity,
      price: this.selectedMedicine.mrp,
      discount: 0,
      total
    });
    this.calculateTotals();
  }

  removeItem(index: number) {
    this.cart.splice(index, 1);
    this.calculateTotals();
  }

  calculateTotals() {
    this.subtotal = this.cart.reduce((sum, i) => sum + i.total, 0);
    this.tax = this.subtotal * 0.05;
    this.grandTotal = this.subtotal + this.tax;
  }

  processSale() {
    alert('Sale processed successfully');
  }

  customers: Customer[] = [];
  searchName: string = '';
  searchPhone: string = '';
  selectedCustomer: Customer | null = null;
  selectedMedicineTmp: (Medicine & { qty?: number; totalPrice?: number }) | null = null;


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


  searchText = '';
  qty = 1;

  mrp = 150.00;
  stock = 120;
  expiry = '2023-08-15';

  filteredMedicines: Medicine[] = [];

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
    this.selectedMedicineTmp = medicine;
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

}
